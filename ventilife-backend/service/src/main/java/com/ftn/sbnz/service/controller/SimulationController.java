package com.ftn.sbnz.service.controller;

import com.ftn.sbnz.model.models.Patient;
import com.ftn.sbnz.model.models.RespiratorDecision;
import com.ftn.sbnz.service.service.SimulationService;
import com.ftn.sbnz.service.util.ResponseMessage;
import com.ftn.sbnz.service.util.Scenario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/simulation")
public class SimulationController {
	private final SimulationService simulationService;
	private final Scenario scenario;

	@Autowired
	public SimulationController(SimulationService simulationService, Scenario scenario) {
		this.simulationService = simulationService;
		this.scenario = scenario;
	}

	@GetMapping(value="/respirator-decision")
	public ResponseEntity<RespiratorDecision> getRespiratorDecision(@RequestBody Patient patient) {
		return ResponseEntity.ok(simulationService.getRespiratorDecision(patient));
	}

	@GetMapping(value="/start-simulation")
	public ResponseEntity<ResponseMessage> startSimulation()  {
		try {
			simulationService.simulate();
			return ResponseEntity.ok(new ResponseMessage("Simulation started."));
		} catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
			return ResponseEntity.ok(new ResponseMessage(e.getMessage()));
		}
    }

	@PostMapping(value = "/set-scenario")
	public ResponseEntity<ResponseMessage> setScenario(@RequestBody Scenario scenario) {
		this.scenario.setScenario(scenario.getScenario());
		return ResponseEntity.ok(new ResponseMessage("Scenario set."));
	}

	@GetMapping(value="/pO2-change")
	public ResponseEntity<ResponseMessage> pO2Change() {
		return ResponseEntity.ok(new ResponseMessage("Scenario set."));
	}

}
