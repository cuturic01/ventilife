package com.ftn.sbnz.service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftn.sbnz.model.events.ChangeEvent;
import com.ftn.sbnz.model.events.InhaleEvent;
import com.ftn.sbnz.model.models.*;
import com.ftn.sbnz.service.util.ResponseMessage;
import org.drools.template.ObjectDataCompiler;
import org.kie.api.KieServices;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.springframework.beans.factory.annotation.Autowired;
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
		changeRecords.add(new ChangeRecord(patient.getId(), 0.0, 0.0, 0.0, "CPAP"));
		RespiratorDecision respiratorDecision = getRespiratorDecision(patient);
		if (respiratorDecision.getFinalDecision())
			patient.setRespiratorMode("CPAP");
	}

	public Patient changePatientState(String name, String command) throws JsonProcessingException {
		String url = "http://localhost:5000/get-" + command + "/" + name;
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.getForObject(url, String.class);
		ChangeEvent changeEvent = objectMapper.readValue(response, ChangeEvent.class);
		System.out.println(changeEvent);

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
		ModeMessage modeMessage = new ModeMessage();
		modeMessage.setPatientId(patient.getId());

		KieSession cepKieSession = createCepChangeKieSession(patient, changeRecord, changeEvent);
		cepKieSession.fireAllRules();

		// TODO: testirati posle
		KieSession forwardKieSession = createForwardKieSession(patient, changeRecord);
		forwardKieSession.fireAllRules();

		System.out.println(changeRecord);

		KieSession backwardKieSession = createBackwardKieSession(patient, changeRecord, modeMessage);
		backwardKieSession.fireAllRules();

		processModeMessage(modeMessage);

		return patient;
	}

	// TODO: momenat prebacivanja moda treba da ima potvrdu
	public ModeMessage changeMode(String patientId, String mode) {
		Patient patient = patients
				.stream()
				.filter(p -> Objects.equals(p.getId().toString(), patientId))
				.findAny()
				.orElse(null);
		ChangeRecord changeRecord = changeRecords
				.stream().filter(cr -> Objects.equals(cr.getPatientId().toString(), patientId))
				.findAny()
				.orElse(null);
		ModeMessage modeMessage = new ModeMessage();
		modeMessage.setPatientId(patient.getId());
		changeRecord.setChosenMode(mode);
		KieSession kieSession = createBackwardKieSession(patient, changeRecord, modeMessage);
		kieSession.fireAllRules();
		processModeMessage(modeMessage);
		patient.setRespiratorMode(mode);

		KieSession simpleKieSession = createSimpleKieSession(patient, changeRecord);
		simpleKieSession.fireAllRules();

		return modeMessage;
	}

	public void badInhalation(String name) throws JsonProcessingException {
		String url = "http://localhost:5000/inhale-event/" + name;
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.getForObject(url, String.class);
		InhaleEvent inhaleEvent = objectMapper.readValue(response, InhaleEvent.class);
		Patient patient = patients
				.stream()
				.filter(p -> Objects.equals(p.getId().toString(), inhaleEvent.getPatientId().toString()))
				.findAny()
				.orElse(null);
		KieSession kieSession = createCepInhalationKieSession(patient, inhaleEvent);
		kieSession.fireAllRules();
	}

	private KieSession createForwardKieSession(Patient patient, ChangeRecord changeRecord) {
		KieSession kieSession = kieContainer.newKieSession("fwKsession");
		kieSession.insert(patient);
		kieSession.insert(changeRecord);
		return kieSession;
	}

	private KieSession createCepChangeKieSession(Patient patient, ChangeRecord changeRecord, ChangeEvent changeEvent) {
		InputStream template = SimulationService.class.getResourceAsStream("/templates/cep.drt");
		List<Thresholds> data = new ArrayList<>();
		data.add(new Thresholds(-0.4, 0.3, -7.5));

		ObjectDataCompiler converter = new ObjectDataCompiler();
		String drl = converter.compile(data, template);

		KieSession kieSession = createKieSessionFromDRL(drl);
		kieSession.insert(patient);
		kieSession.insert(changeRecord);
		kieSession.insert(changeEvent);
		return kieSession;
	}

	private KieSession createCepInhalationKieSession(Patient patient, InhaleEvent inhaleEvent) {
		KieSession kieSessionCep = kieContainer.newKieSession("cepKsession");
		kieSessionCep.insert(patient);
		kieSessionCep.insert(inhaleEvent);
		return kieSessionCep;
	}

	private KieSession createBackwardKieSession(Patient patient, ChangeRecord changeRecord, ModeMessage modeMessage) {
		InputStream template = SimulationService.class.getResourceAsStream("/templates/backward.drt");

		List<ChangeRecord> data = new ArrayList<>();
		System.out.println(changeRecord);
		data.add(changeRecord);
//		ChangeRecord changeRecord1 = new ChangeRecord(patient.getId(), -0.77, 0.569, 85.03, "CPAP");
//		data.add(changeRecord1);

		ObjectDataCompiler converter = new ObjectDataCompiler();
		String drl = converter.compile(data, template);

		KieSession kieSessionBackward = createKieSessionFromDRL(drl);
		kieSessionBackward.insert(new RespiratorMode(
				"Spontaneous",
				"Mode",
				true,
				70.0,
				100.0,
				-1.6,
				0.0,
				0.0,
				1.2

		));
		kieSessionBackward.insert(new RespiratorMode(
				"CPAP",
				"Spontaneous",
				true,
				85.0,
				100.0,
				-0.8,
				0.0,
				0.0,
				0.6
		));
		kieSessionBackward.insert(new RespiratorMode(
				"APRV",
				"Spontaneous",
				true,
				70.0,
				85.0,
				-1.6,
				-0.8,
				0.6,
				1.2
		));

		kieSessionBackward.insert(new RespiratorMode(
				"Assisted",
				"Mode",
				false,
				25.0,
				70.0,
				-4.0,
				-1.6,
				1.2,
				3.0
		));
		kieSessionBackward.insert(new RespiratorMode(
				"SIMV",
				"Assisted",
				false,
				47.5,
				70.0,
				-2.8,
				-1.6,
				1.2,
				2.1
		));
		kieSessionBackward.insert(new RespiratorMode(
				"KMV_AC",
				"Assisted",
				false,
				25.0,
				47.5,
				-4.0,
				-2.8,
				2.1,
				3.0
		));

		kieSessionBackward.insert(patient);
		kieSessionBackward.insert(modeMessage);
		return kieSessionBackward;
	}

	private KieSession createSimpleKieSession(Patient patient, ChangeRecord changeRecord) {
		KieSession kieSession = kieContainer.newKieSession("simpleKsession");
		kieSession.insert(patient);
		kieSession.insert(changeRecord);
		return kieSession;
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

	private void processModeMessage(ModeMessage modeMessage) {
		if (modeMessage.getModeConfirmation() == null)
			modeMessage.setModeConfirmation("Chosen mode is not appropriate.");
	}
}
