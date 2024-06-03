package com.ftn.sbnz.service.service;

import com.ftn.sbnz.model.models.Patient;
import com.ftn.sbnz.model.models.RespiratorDecision;
import com.ftn.sbnz.service.util.Scenario;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class SimulationService {

	private final KieContainer kieContainer;
	private final Scenario scenario;
	private Patient patient;

	@Autowired
	public SimulationService(KieContainer kieContainer, Scenario scenario) {
		this.kieContainer = kieContainer;
		this.scenario = scenario;
	}

	public RespiratorDecision getRespiratorDecision(Patient patient) {
		this.patient = patient;
		KieSession kieSession = kieContainer.newKieSession("fwKsession");

		RespiratorDecision respiratorDecision = new RespiratorDecision();
		respiratorDecision.setPatientId(this.patient.getId());
		kieSession.insert(this.patient);
		kieSession.insert(respiratorDecision);
		kieSession.fireAllRules();

		return respiratorDecision;
	}

	public void simulate() throws Exception {
		if (this.patient == null)
			throw new Exception("No patient information.");
		this.scenario.setScenario("simulating");

		while(true) {
			if (Objects.equals(scenario.getScenario(), "simulating")) {
				Thread.sleep((long) 100.0);
			} else if (Objects.equals(scenario.getScenario(), "stop")) {
				scenario.setScenario("");
				return;
			} else if (Objects.equals(scenario.getScenario(), "pO2Change")) {
				System.out.println("pO2Change");
			}
		}
	}

	public void pO2Change() {

	}
}
