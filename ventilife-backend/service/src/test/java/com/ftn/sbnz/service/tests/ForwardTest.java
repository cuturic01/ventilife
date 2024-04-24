package com.ftn.sbnz.service.tests;

import com.ftn.sbnz.model.models.Patient;
import com.ftn.sbnz.model.models.RespiratorDecision;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class ForwardTest {

    @Test
    public void testForward() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession kieSession = kContainer.newKieSession("fwKsession");

        Patient patient = new Patient();
        patient.setFrequency(36);
        patient.setTidalVolume(4.0);
        patient.setPCO2(8.9);
        patient.setPO2(6.0);

        RespiratorDecision respiratorDecision = new RespiratorDecision();
        respiratorDecision.setPatientId(patient.getId());

        kieSession.insert(patient);
        kieSession.insert(respiratorDecision);
        kieSession.fireAllRules();

        System.out.println(respiratorDecision);
    }
}
