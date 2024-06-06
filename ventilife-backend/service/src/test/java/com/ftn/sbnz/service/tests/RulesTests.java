package com.ftn.sbnz.service.tests;

import com.ftn.sbnz.model.events.ChangeEvent;
import com.ftn.sbnz.model.events.InhaleEvent;
import com.ftn.sbnz.model.events.PO2Change;
import com.ftn.sbnz.model.models.*;
import org.drools.template.ObjectDataCompiler;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.kie.internal.utils.KieHelper;

import java.io.InputStream;
import java.util.*;

public class RulesTests {

    @Test
    public void testRespiratorDecision() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession kieSession = kContainer.newKieSession("fwKsession");

        Patient patient = new Patient();
        patient.setFrequency(36);
        patient.setTidalVolume(500.0);
        patient.setpCO2(8.9);
        patient.setpO2(6.0);

        RespiratorDecision respiratorDecision = new RespiratorDecision();
        respiratorDecision.setPatientId(patient.getId());

        kieSession.insert(patient);
        kieSession.insert(respiratorDecision);
        kieSession.fireAllRules();

        System.out.println(respiratorDecision);
    }

    @Test
    public void testNormalParams() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession kieSession = kContainer.newKieSession("fwKsession");

        Patient patient = new Patient();
        patient.setCompliance(5.0);
        patient.setWeight(100.0);
        patient.setResistance(10.0);
        kieSession.insert(patient);

        StablePatientParams sp = new StablePatientParams();
        sp.setPatientId(patient.getId());
        kieSession.insert(sp);

        kieSession.fireAllRules();

    }

    @Test
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

    @Test
    public void testCEP() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession kieSessionCep = kContainer.newKieSession("cepKsession");
        KieSession kieSessionBackward = kContainer.newKieSession("bwKsession");
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

        Patient patient = new Patient();
        patient.setFiO2(25.0);
        patient.setRespiratorMode("SIMV");
        patient.setMinuteVolume(500.0 * 6);
        patient.setpCO2(5.0);
        patient.setpO2(10.0);
        patient.setParticipationPercentage(30.0);
        patient.setConscious(false);
        patient.setVolumeControlled(true);
        patient.setCompliance(4.0);
        patient.setGasFlow(6.0);
        patient.setResistance(0.245);
        patient.setWeight(95.0);
        kieSessionCep.insert(patient);

        ChangeRecord changeRecord = new ChangeRecord(patient.getId(), 0.0, 0.0, 0.0);
        kieSessionCep.insert(changeRecord);

        kieSessionCep.insert(new ChangeEvent(patient.getId(), -1.5, 0.5, 25.0));
        kieSessionCep.insert(new ChangeEvent(patient.getId(), -1.5, 0.5, 25.0));
        kieSessionCep.insert(new ChangeEvent(patient.getId(), -1.5, 0.5, 25.0));
        kieSessionCep.insert(new ChangeEvent(patient.getId(), -1.5, 0.5, 25.0));

        kieSessionCep.insert(new InhaleEvent(patient.getId(), 500.0 * 7));

        kieSessionCep.fireAllRules();
        System.out.println(changeRecord);
        changeRecord.setChosenMode("APRV");

        kieSessionBackward.insert(patient);
        kieSessionBackward.insert(changeRecord);

        kieSessionBackward.fireAllRules();

    }

    @Test
    public void testBackwardTemplate() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession kieSessionCep = kContainer.newKieSession("cepKsession");

        Patient patient = new Patient();
        patient.setFiO2(25.0);
        patient.setRespiratorMode("CPAP");
        patient.setMinuteVolume(500.0 * 6);
        patient.setpCO2(5.0);
        patient.setpO2(10.0);
        patient.setParticipationPercentage(100.0);
        patient.setConscious(true);
        kieSessionCep.insert(patient);

        ChangeRecord changeRecord = new ChangeRecord(patient.getId(), 0.0, 0.0, 0.0);
        kieSessionCep.insert(changeRecord);

        kieSessionCep.insert(new ChangeEvent(patient.getId(), -1.5, 0.5, 25.0));
        kieSessionCep.insert(new ChangeEvent(patient.getId(), -1.5, 0.5, 25.0));
        kieSessionCep.insert(new ChangeEvent(patient.getId(), -1.5, 0.5, 25.0));

        kieSessionCep.fireAllRules();
        System.out.println(changeRecord);
        changeRecord.setChosenMode("APRV");

        InputStream template = RulesTests.class.getResourceAsStream("/templates/backward.drt");

        List<ChangeRecord> data = new ArrayList<>();
        data.add(changeRecord);

        ObjectDataCompiler converter = new ObjectDataCompiler();
        String drl = converter.compile(data, template);

        System.out.println(drl);

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
        //kieSessionBackward.insert(changeRecord);

        kieSessionBackward.fireAllRules();
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

    @Test
    public void testCepTemplate() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession kieSessionBackward = kContainer.newKieSession("bwKsession");
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

        Patient patient = new Patient();
        patient.setFiO2(25.0);
        patient.setRespiratorMode("SIMV");
        patient.setMinuteVolume(500.0 * 6);
        patient.setpCO2(5.0);
        patient.setpO2(10.0);
        patient.setParticipationPercentage(30.0);
        patient.setConscious(false);
        patient.setVolumeControlled(true);
        patient.setCompliance(4.0);
        patient.setGasFlow(6.0);
        patient.setResistance(0.245);
        patient.setWeight(95.0);

        InputStream template = RulesTests.class.getResourceAsStream("/templates/cep.drt");
        List<Thresholds> data = new ArrayList<>();
        data.add(new Thresholds(-1.5, 0.5, 25.0));

        ObjectDataCompiler converter = new ObjectDataCompiler();
        String drl = converter.compile(data, template);

        KieSession kieSessionCep = createKieSessionFromDRL(drl);

        kieSessionCep.insert(patient);

        ChangeRecord changeRecord = new ChangeRecord(patient.getId(), 0.0, 0.0, 0.0);
        kieSessionCep.insert(changeRecord);

        kieSessionCep.insert(new ChangeEvent(patient.getId(), -1.5, 0.5, 25.0));
        kieSessionCep.insert(new ChangeEvent(patient.getId(), -1.5, 0.5, 25.0));
        kieSessionCep.insert(new ChangeEvent(patient.getId(), -1.5, 0.5, 25.0));
        kieSessionCep.insert(new ChangeEvent(patient.getId(), -1.5, 0.5, 25.0));

        kieSessionCep.insert(new InhaleEvent(patient.getId(), 500.0 * 7));

        kieSessionCep.fireAllRules();
        System.out.println(changeRecord);
        changeRecord.setChosenMode("APRV");

        kieSessionBackward.insert(patient);
        kieSessionBackward.insert(changeRecord);

        kieSessionBackward.fireAllRules();
    }

    @Test
    public void testQuery() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession kieSession = kContainer.newKieSession("queryKsession");

        Patient patient = new Patient();
        ChangeRecord changeRecord = new ChangeRecord(patient.getId(), -5.0, 5.0, 0.0);
        Patient patient1 = new Patient();
        ChangeRecord changeRecord1 = new ChangeRecord(patient1.getId(), -7.0, 7.0, 0.0);
        Patient patient2 = new Patient();
        ChangeRecord changeRecord2 = new ChangeRecord(patient2.getId(), -9.0, 9.0, 0.0);
        System.out.println(patient2.getId());

        kieSession.insert(patient);
        kieSession.insert(patient1);
        kieSession.insert(patient2);
        kieSession.insert(changeRecord);
        kieSession.insert(changeRecord1);
        kieSession.insert(changeRecord2);

        kieSession.fireAllRules();

//        QueryResults results = kieSession.getQueryResults("getMaxDeltaPCO2Patient");
//
//        Set<Patient> uniquePatients = new HashSet<>();
//        for (QueryResultsRow row : results) {
//            Patient p = (Patient) row.get("$patient");
//            uniquePatients.add(p);
//        }
//
//        for (Patient p : uniquePatients) {
//            System.out.println(p);
//        }

        QueryResults results = kieSession.getQueryResults("getMinDeltaPO2Patient");

        Set<Patient> uniquePatients = new HashSet<>();
        for (QueryResultsRow row : results) {
            Patient p = (Patient) row.get("$patient");
            uniquePatients.add(p);
        }

        for (Patient p : uniquePatients) {
            System.out.println(p);
        }

    }

}
