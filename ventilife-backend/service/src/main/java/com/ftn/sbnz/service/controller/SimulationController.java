package com.ftn.sbnz.service.controller;

import com.ftn.sbnz.model.models.Patient;
import com.ftn.sbnz.model.models.RespiratorDecision;
import com.ftn.sbnz.service.service.SimulationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ftn.sbnz.model.events.Item;


@RestController
@RequestMapping("api/simulation")
public class SimulationController {
	private final SimulationService simulationService;

	@Autowired
	public SimulationController(SimulationService simulationService) {
		this.simulationService = simulationService;
	}

	@RequestMapping(value = "/item", method = RequestMethod.GET, produces = "application/json")
	public Item getQuestions(@RequestParam(required = true) String id, @RequestParam(required = true) String name,
			@RequestParam(required = true) double cost, @RequestParam(required = true) double salePrice) {

		Item newItem = new Item(Long.parseLong(id), name, cost, salePrice);
		Item i2 = simulationService.getClassifiedItem(newItem);

		return i2;
	}

	@GetMapping(value="/respirator-decision")
	public ResponseEntity<RespiratorDecision> getRespiratorDecision(@RequestBody Patient patient) {
		return ResponseEntity.ok(simulationService.getRespiratorDecision(patient));
	}

}
