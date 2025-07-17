package com.ftn.sbnz.service;

import com.ftn.sbnz.service.service.SimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitialDataLoader implements CommandLineRunner {

    @Autowired
    private SimulationService simulationService;

    @Override
    public void run(String... args) throws Exception {
        simulationService.addPatient("pera");
        simulationService.addPatient("marko");
        simulationService.addPatient("jovan");
    }
}
