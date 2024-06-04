package com.ftn.sbnz.service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftn.sbnz.model.models.Patient;
import com.ftn.sbnz.model.models.RespiratorDecision;
import com.ftn.sbnz.model.models.StablePatientParams;
import com.ftn.sbnz.service.util.Scenario;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;


@Service
public class SimulationService {

	private final KieContainer kieContainer;
	private List<Patient> patients;
	private List<StablePatientParams> stablePatientParamsList;
	private ObjectMapper objectMapper;

	@Autowired
	public SimulationService(
			KieContainer kieContainer,
			List<Patient> patients,
			ObjectMapper objectMapper,
			List<StablePatientParams> stablePatientParamsList
			) {
		this.kieContainer = kieContainer;
		this.patients = patients;
		this.objectMapper = objectMapper;
		this.stablePatientParamsList = stablePatientParamsList;
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
		patient.setFiO2(30.0);
		System.out.println(patient);
		patients.add(patient);
		stablePatientParamsList.add(getStablePatientParams(patient));
		getRespiratorDecision(patient);
	}
}
