package com.ftn.sbnz.service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftn.sbnz.model.events.ChangeEvent;
import com.ftn.sbnz.model.events.InhaleEvent;
import com.ftn.sbnz.model.models.*;
import com.ftn.sbnz.service.dto.PatientDataDTO;
import com.ftn.sbnz.service.dto.PatientResponseDTO;
import org.drools.template.ObjectDataCompiler;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.kie.internal.utils.KieHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.util.*;

@Service
public class SimulationService {

	private final KieContainer kieContainer;
	private List<Patient> patients;
	private List<StablePatientParams> stablePatientParamsList;
	private List<ChangeRecord> changeRecords;
	private List<RespiratorDecision> respiratorDecisions;
	private ObjectMapper objectMapper;

	@Autowired
	public SimulationService(
			KieContainer kieContainer,
			List<Patient> patients,
			ObjectMapper objectMapper,
			List<StablePatientParams> stablePatientParamsList,
			List<ChangeRecord> changeRecords,
			List<RespiratorDecision> respiratorDecisions

	) {
		this.kieContainer = kieContainer;
		this.patients = patients;
		this.objectMapper = objectMapper;
		this.stablePatientParamsList = stablePatientParamsList;
		this.changeRecords = changeRecords;
		this.respiratorDecisions = respiratorDecisions;
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
		respiratorDecisions.add(respiratorDecision);
		if (respiratorDecision.getFinalDecision())
			patient.setRespiratorMode("CPAP");
	}

	public PatientResponseDTO changePatientState(String name, String command) throws JsonProcessingException {
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
		ResponseMessage responseMessage = new ResponseMessage();

		KieSession cepKieSession = createCepChangeKieSession(patient, changeRecord, changeEvent, responseMessage);
		cepKieSession.fireAllRules();

		KieSession forwardKieSession = createForwardKieSession(patient, changeRecord);
		forwardKieSession.fireAllRules();

		System.out.println(changeRecord);

		KieSession backwardKieSession = createBackwardKieSession(patient, changeRecord, modeMessage);
		backwardKieSession.fireAllRules();

		processModeMessage(modeMessage);

		return new PatientResponseDTO(patient, responseMessage, changeRecord);
	}

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

	public PatientResponseDTO badInhalation(String name) throws JsonProcessingException {
		String url = "http://localhost:5000/inhale-event/" + name;
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.getForObject(url, String.class);
		InhaleEvent inhaleEvent = objectMapper.readValue(response, InhaleEvent.class);
		Patient patient = patients
				.stream()
				.filter(p -> Objects.equals(p.getId().toString(), inhaleEvent.getPatientId().toString()))
				.findAny()
				.orElse(null);
		ResponseMessage responseMessage = new ResponseMessage();
		KieSession kieSession = createCepInhalationKieSession(patient, inhaleEvent, responseMessage);
		kieSession.fireAllRules();
		return new PatientResponseDTO(patient, responseMessage, null);
	}


	public Patient getMinPO2() {
		KieSession kieSession = createQueryKieSession();
		kieSession.fireAllRules();
		Patient patient = new Patient();

		QueryResults results = kieSession.getQueryResults("getMinDeltaPO2Patient");
		for (QueryResultsRow row : results) {
			Patient p = (Patient) row.get("$patient");
			patient = p;
		}

		return patient;
	}

	public Patient getMaxPCO2() {
		KieSession kieSession = createQueryKieSession();
		kieSession.fireAllRules();
		Patient patient = new Patient();

		QueryResults results = kieSession.getQueryResults("getMaxDeltaPCO2Patient");
		for (QueryResultsRow row : results) {
			Patient p = (Patient) row.get("$patient");
			patient = p;
		}

		return patient;
	}

	private KieSession createForwardKieSession(Patient patient, ChangeRecord changeRecord) {
		KieSession kieSession = kieContainer.newKieSession("fwKsession");
		kieSession.insert(patient);
		kieSession.insert(changeRecord);
		return kieSession;
	}

	private KieSession createCepChangeKieSession(Patient patient, ChangeRecord changeRecord, ChangeEvent changeEvent, ResponseMessage responseMessage) {
		InputStream template = SimulationService.class.getResourceAsStream("/templates/cep.drt");
		List<Thresholds> data = new ArrayList<>();
		data.add(new Thresholds(-0.4, 0.3, -7.5));

		ObjectDataCompiler converter = new ObjectDataCompiler();
		String drl = converter.compile(data, template);

		KieSession kieSession = createKieSessionFromDRL(drl);
		kieSession.insert(patient);
		kieSession.insert(changeRecord);
		kieSession.insert(changeEvent);
		kieSession.insert(responseMessage);
		return kieSession;
	}

	private KieSession createCepInhalationKieSession(Patient patient, InhaleEvent inhaleEvent, ResponseMessage responseMessage) {
		KieSession kieSessionCep = kieContainer.newKieSession("cepKsession");
		kieSessionCep.insert(responseMessage);
		kieSessionCep.insert(patient);
		kieSessionCep.insert(inhaleEvent);
		return kieSessionCep;
	}

	private KieSession createBackwardKieSession(Patient patient, ChangeRecord changeRecord, ModeMessage modeMessage) {
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

	private KieSession createQueryKieSession() {
		KieSession kieSession = kieContainer.newKieSession("queryKsession");

		for (Patient patient : patients)
			kieSession.insert(patient);

		for (ChangeRecord changeRecord : changeRecords)
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

	public Patient getPatient(UUID id) {
		for (Patient patient: patients) {
			if (patient.getId().equals(id)) {
				return patient;
			}
		}
		return null;
	}

	public StablePatientParams getStablePatientParams(UUID id) {
		for (StablePatientParams patient: stablePatientParamsList) {
			if (patient.getPatientId().equals(id)) {
				return patient;
			}
		}
		return null;
	}

	public ChangeRecord getChangeRecord(UUID id) {
		for (ChangeRecord patient: changeRecords) {
			if (patient.getPatientId().equals(id)) {
				return patient;
			}
		}
		return null;
	}

	public RespiratorDecision getRespiratorDecision(UUID id) {
		for (RespiratorDecision respiratorDecision: respiratorDecisions) {
			if (respiratorDecision.getPatientId().equals(id)) {
				return respiratorDecision;
			}
		}
		return null;
	}

	public PatientDataDTO getPatientData(UUID id) {
		Patient patient = getPatient(id);
		StablePatientParams stablePatientParams = getStablePatientParams(id);
		ChangeRecord changeRecord = getChangeRecord(id);
		RespiratorDecision respiratorDecision = getRespiratorDecision(id);
		return new PatientDataDTO(patient, stablePatientParams, changeRecord, respiratorDecision);
	}

	public List<PatientDataDTO> getPatientsData() {
		List<PatientDataDTO> patientDataDTOS = new ArrayList<>();
		for (Patient patient: patients) {
			PatientDataDTO patientDataDTO = new PatientDataDTO();
			patientDataDTO.setPatient(patient);
			for (StablePatientParams stablePatientParams: stablePatientParamsList) {
				if (patient.getId().equals(stablePatientParams.getPatientId())) {
					patientDataDTO.setStablePatientParams(stablePatientParams);
					break;
				}
			}
			for (ChangeRecord changeRecord: changeRecords) {
				if (patient.getId().equals(changeRecord.getPatientId())) {
					patientDataDTO.setChangeRecord(changeRecord);
					break;
				}
			}
			for (RespiratorDecision respiratorDecision: respiratorDecisions) {
				if (patient.getId().equals(respiratorDecision.getPatientId())) {
					patientDataDTO.setRespiratorDecision(respiratorDecision);
					break;
				}
			}
			patientDataDTOS.add(patientDataDTO);
		}
		return patientDataDTOS;
	}
}
