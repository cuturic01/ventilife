package com.ftn.sbnz.service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftn.sbnz.model.events.ChangeEvent;
import com.ftn.sbnz.model.models.*;
import com.ftn.sbnz.service.util.Scenario;
import org.drools.template.ObjectDataCompiler;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
public class SimulationService {

	private final KieContainer kieContainer;
	private List<Patient> patients;
	private List<StablePatientParams> stablePatientParamsList;
	private List<ChangeRecord> changeRecords;
	private ObjectMapper objectMapper;

	@Autowired
	public SimulationService(
			KieContainer kieContainer,
			List<Patient> patients,
			ObjectMapper objectMapper,
			List<StablePatientParams> stablePatientParamsList,
			List<ChangeRecord> changeRecords
	) {
		this.kieContainer = kieContainer;
		this.patients = patients;
		this.objectMapper = objectMapper;
		this.stablePatientParamsList = stablePatientParamsList;
		this.changeRecords = changeRecords;
	}

	public RespiratorDecision getRespiratorDecision(Patient patient) {
		KieSession kieSession = kieContainer.newKieSession("fwKsession");

		RespiratorDecision respiratorDecision = new RespiratorDecision();
		respiratorDecision.setPatientId(patient.getId());
		kieSession.insert(patient);
		kieSession.insert(respiratorDecision);
		kieSession.fireAllRules();

		return respiratorDecision;
	}

	public StablePatientParams getStablePatientParams(Patient patient) {
		KieSession kieSession = kieContainer.newKieSession("fwKsession");

		StablePatientParams stablePatientParams = new StablePatientParams();
		stablePatientParams.setPatientId(patient.getId());
		kieSession.insert(patient);
		kieSession.insert(stablePatientParams);
		kieSession.fireAllRules();

		return stablePatientParams;
	}

	public void addPatient(String name) throws JsonProcessingException {
		String url = "http://localhost:5000/";
		RestTemplate restTemplate = new RestTemplate();
		String response = "";
		if (Objects.equals(name, "pera")) {
			response = restTemplate.getForObject(url + "pera-data", String.class);
		} else if (Objects.equals(name, "jovan")) {
			response = restTemplate.getForObject(url + "jovan-data", String.class);
		} else if (Objects.equals(name, "marko")) {
			response = restTemplate.getForObject(url + "marko-data", String.class);
		}
		System.out.println("Response: " + response);
		Patient patient = objectMapper.readValue(response, Patient.class);
		patient.setFiO2(25.0);
		System.out.println(patient);
		patients.add(patient);
		stablePatientParamsList.add(getStablePatientParams(patient));
		changeRecords.add(new ChangeRecord(patient.getId(), 0.0, 0.0, 0.0, "APRV"));
		RespiratorDecision respiratorDecision = getRespiratorDecision(patient);
		if (respiratorDecision.getFinalDecision())
			patient.setRespiratorMode("CPAP");
	}

	public Patient getWorse(String name) throws JsonProcessingException {
		String url = "http://localhost:5000/get-worse/" + name;
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.getForObject(url, String.class);
		ChangeEvent changeEvent = objectMapper.readValue(response, ChangeEvent.class);

		Patient patient = patients
				.stream()
				.filter(p -> Objects.equals(p.getId().toString(), changeEvent.getPatientId().toString()))
				.findAny()
				.orElse(null);
		ChangeRecord changeRecord = changeRecords
				.stream().filter(cr -> Objects.equals(cr.getPatientId().toString(), patient.getId().toString()))
				.findAny()
				.orElse(null);
		System.out.println(patient);

		KieSession cepKieSession = createCepKieSession(patient, changeRecord, changeEvent);
		cepKieSession.fireAllRules();
		KieSession backwardKieSession = createBackwardKieSession(patient, changeRecord);
		backwardKieSession.fireAllRules();

		return patient;
	}

	private KieSession createCepKieSession(Patient patient, ChangeRecord changeRecord, ChangeEvent changeEvent) {
		InputStream template = SimulationService.class.getResourceAsStream("/templates/cep.drt");
		List<Thresholds> data = new ArrayList<>();
		data.add(new Thresholds(-1.5, 0.5, 25.0));

		ObjectDataCompiler converter = new ObjectDataCompiler();
		String drl = converter.compile(data, template);

		KieSession kieSession = createKieSessionFromDRL(drl);
		kieSession.insert(patient);
		kieSession.insert(changeRecord);
		kieSession.insert(changeEvent);
		return kieSession;
	}

	private KieSession createBackwardKieSession(Patient patient, ChangeRecord changeRecord) {
		InputStream template = SimulationService.class.getResourceAsStream("/templates/backward.drt");

		List<ChangeRecord> data = new ArrayList<>();
		data.add(changeRecord);

		ObjectDataCompiler converter = new ObjectDataCompiler();
		String drl = converter.compile(data, template);

		KieSession kieSessionBackward = createKieSessionFromDRL(drl);
		kieSessionBackward.insert(new RespiratorMode(
				"Spontaneous",
				"Mode",
				true,
				50.0,
				100.0,
				-6.0,
				-1.0,
				0.0,
				1.0

		));
		kieSessionBackward.insert(new RespiratorMode(
				"CPAP",
				"Spontaneous",
				true,
				85.0,
				100.0,
				-4.0,
				-1.0,
				0.0,
				1.0
		));
		kieSessionBackward.insert(new RespiratorMode(
				"APRV",
				"Spontaneous",
				true,
				50.0,
				85.0,
				-6.0,
				-4.0,
				0.0,
				1.0
		));

		kieSessionBackward.insert(new RespiratorMode(
				"Assisted",
				"Mode",
				false,
				0.0,
				50.0,
				-10.0,
				-6.0,
				1.0,
				10.0
		));
		kieSessionBackward.insert(new RespiratorMode(
				"SIMV",
				"Assisted",
				false,
				10.0,
				50.0,
				-8.0,
				-6.0,
				1.0,
				5.0
		));
		kieSessionBackward.insert(new RespiratorMode(
				"KMV/AC",
				"Assisted",
				false,
				0.0,
				10.0,
				-10.0,
				-8.0,
				5.0,
				10.0
		));

		kieSessionBackward.insert(patient);
		return kieSessionBackward;
	}

	private KieSession createKieSessionFromDRL(String drl){
		KieHelper kieHelper = new KieHelper();
		kieHelper.addContent(drl, ResourceType.DRL);

		Results results = kieHelper.verify();

		if (results.hasMessages(Message.Level.WARNING, Message.Level.ERROR)){
			List<Message> messages = results.getMessages(Message.Level.WARNING, Message.Level.ERROR);
			for (Message message : messages) {
				System.out.println("Error: "+message.getText());
			}

			throw new IllegalStateException("Compilation errors were found. Check the logs.");
		}

		return kieHelper.build().newKieSession();
	}
}
