package com.ftn.sbnz.service.service;

import com.ftn.sbnz.model.models.Patient;
import com.ftn.sbnz.model.models.RespiratorDecision;
import com.ftn.sbnz.service.util.Scenario;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.sbnz.model.events.Item;

import java.util.Objects;


@Service
public class SimulationService {

	private static Logger log = LoggerFactory.getLogger(SimulationService.class);

	private final KieContainer kieContainer;
	private final Scenario scenario;

	@Autowired
	public SimulationService(KieContainer kieContainer, Scenario scenario) {
		log.info("Initialising a new example session.");
		this.kieContainer = kieContainer;
		this.scenario = scenario;
	}

	public Item getClassifiedItem(Item i) {
		KieSession kieSession = kieContainer.newKieSession();
		kieSession.insert(i);
		kieSession.fireAllRules();
		kieSession.dispose();
		return i;
	}

	public RespiratorDecision getRespiratorDecision(Patient patient) {
		KieSession kieSession = kieContainer.newKieSession("fwKsession");

		System.out.println(patient.getpCO2());

		RespiratorDecision respiratorDecision = new RespiratorDecision();
		respiratorDecision.setPatientId(patient.getId());
		kieSession.insert(patient);
		kieSession.insert(respiratorDecision);
		kieSession.fireAllRules();

		return respiratorDecision;
	}

	public void simulate(Patient patient) throws InterruptedException {
		while(true) {
			if (Objects.equals(scenario.getScenario(), "")) {
				Thread.sleep((long) 100.0);
			} else if (Objects.equals(scenario.getScenario(), "")) {

			}
		}
	}
}
