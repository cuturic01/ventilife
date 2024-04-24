package com.ftn.sbnz.service.tests;

import com.ftn.sbnz.model.events.Notification;
import com.ftn.sbnz.model.models.Certificate;
import org.junit.Test;
 import org.kie.api.KieServices;
 import org.kie.api.runtime.KieContainer;
 import org.kie.api.runtime.KieSession;



public class CEPConfigTest {

    @Test
    public void testCep() {
         KieServices ks = KieServices.Factory.get();
         KieContainer kContainer = ks.getKieClasspathContainer();
         KieSession ksession = kContainer.newKieSession("cepKsession");

         ksession.insert(new Notification(1L));
         ksession.insert(new Notification(1L));
         ksession.insert(new Notification(1L));
         ksession.insert(new Notification(1L));
         ksession.insert(new Notification(1L));
         ksession.insert(new Notification(1L));
         ksession.insert(new Notification(1L));

         ksession.fireAllRules();
    }

    @Test
    public void testBw() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession ksession = kContainer.newKieSession("bwKsession");

        ksession.insert("facebook");

        ksession.insert(new Certificate("DigiCert Root CA", "Certificate", "validan"));

        ksession.insert(new Certificate("DigiCert Server1 CA", "DigiCert Root CA", "validan"));
        ksession.insert(new Certificate("www.facebook.com", "DigiCert Server1 CA", "validan"));

        ksession.insert(new Certificate("DigiCert Server2 CA", "DigiCert Root CA", "validan"));

        ksession.insert(new Certificate("DigiCert Global Root CA", "Certificate", "validan"));

        ksession.insert(new Certificate("DigiCert TLS CA", "DigiCert Global Root CA", "validan"));
        ksession.insert(new Certificate("www.netflix.com", "DigiCert TLS CA", "validan"));

        ksession.insert(new Certificate("Microsoft TLS CA", "DigiCert Global Root CA", "nevalidan"));
        ksession.insert(new Certificate("www.microsoft1.com", "Microsoft TLS CA", "validan"));
        ksession.insert(new Certificate("www.microsoft2.com", "Microsoft TLS CA", "validan"));

        ksession.fireAllRules();

    }
}
