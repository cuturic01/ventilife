package com.ftn.sbnz.service.tests;

import com.ftn.sbnz.model.events.PO2Change;
import com.ftn.sbnz.model.models.Patient;
import com.ftn.sbnz.model.models.RespiratorDecision;
import com.ftn.sbnz.model.models.RespiratorMode;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class RulesTests {

    @org.junit.Test
    public void testForward() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession kieSession = kContainer.newKieSession("fwKsession");

        Patient patient = new Patient();
        patient.setFrequency(36);
        patient.setTidalVolume(4.0);
        patient.setpCO2(8.9);
        patient.setpO2(6.0);

        RespiratorDecision respiratorDecision = new RespiratorDecision();
        respiratorDecision.setPatientId(patient.getId());

        kieSession.insert(patient);
        kieSession.insert(respiratorDecision);
        kieSession.fireAllRules();

        System.out.println(respiratorDecision);
    }

    @org.junit.Test
    public void testBackward() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession kieSession = kContainer.newKieSession("bwKsession");

        kieSession.insert(new RespiratorMode(
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
        kieSession.insert(new RespiratorMode(
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
        kieSession.insert(new RespiratorMode(
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

        kieSession.insert(new RespiratorMode(
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
        kieSession.insert(new RespiratorMode(
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
        kieSession.insert(new RespiratorMode(
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

        kieSession.fireAllRules();
    }

    @org.junit.Test
    public void testCEP1() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession kieSession = kContainer.newKieSession("cepKsession");

        Patient patient = new Patient();
        patient.setFiO2(25.0);
        patient.setRespiratorMode("CPAP");

        kieSession.insert(patient);
        kieSession.insert(new PO2Change(patient.getId(), -2.6));
        kieSession.insert(new PO2Change(patient.getId(), -2.6));
        kieSession.insert(new PO2Change(patient.getId(), -2.6));
        kieSession.insert(new PO2Change(patient.getId(), -2.6));
        kieSession.insert(new PO2Change(patient.getId(), -2.6));
        kieSession.insert(new PO2Change(patient.getId(), -2.6));

        kieSession.fireAllRules();
    }
}
