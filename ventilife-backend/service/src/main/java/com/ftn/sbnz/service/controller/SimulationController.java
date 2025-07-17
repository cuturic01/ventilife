package com.ftn.sbnz.service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ftn.sbnz.model.models.ModeMessage;
import com.ftn.sbnz.model.models.Patient;
import com.ftn.sbnz.model.models.RespiratorDecision;
import com.ftn.sbnz.service.dto.PatientDataDTO;
import com.ftn.sbnz.service.dto.PatientResponseDTO;
import com.ftn.sbnz.service.service.SimulationService;
import com.ftn.sbnz.model.models.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("api/simulation")
public class SimulationController {
	private final SimulationService simulationService;

	@Autowired
	public SimulationController(SimulationService simulationService) {
		this.simulationService = simulationService;
	}

	@GetMapping(value="/respirator-decision")
	public ResponseEntity<RespiratorDecision> getRespiratorDecision(@RequestBody Patient patient) {
		return ResponseEntity.ok(simulationService.getRespiratorDecision(patient));
	}

	@GetMapping(value="/pO2-change")
	public ResponseEntity<ResponseMessage> pO2Change() {
		return ResponseEntity.ok(new ResponseMessage("Scenario set."));
	}

	@GetMapping(value = "/add-patient/{name}")
	public ResponseEntity<ResponseMessage> addPatient(@PathVariable String name) throws JsonProcessingException {
		simulationService.addPatient(name);
		return ResponseEntity.ok(new ResponseMessage(name + " added."));
	}

	@GetMapping(value = "/get-worse/{name}")
	public ResponseEntity<PatientResponseDTO> getWorse(@PathVariable String name) throws JsonProcessingException {
		return ResponseEntity.ok(simulationService.changePatientState(name, "worse"));
	}

	@GetMapping(value = "/get-better/{name}")
	public ResponseEntity<PatientResponseDTO> getBetter(@PathVariable String name) throws JsonProcessingException {
		return ResponseEntity.ok(simulationService.changePatientState(name, "better"));
	}

	@GetMapping(value = "/change-mode/{patientId}/{mode}")
	public ResponseEntity<ModeMessage> changeMode(@PathVariable String patientId, @PathVariable String mode) {
		return ResponseEntity.ok(simulationService.changeMode(patientId, mode));
	}

	@GetMapping(value = "/bad-inhalation/{name}")
	public ResponseEntity<PatientResponseDTO> badInhalation(@PathVariable String name) throws JsonProcessingException {
		return ResponseEntity.ok(simulationService.badInhalation(name));
	}

	@PreAuthorize("hasRole('USER')")
	@GetMapping(value = "/get-min-pO2")
	public ResponseEntity<Patient> getMinPO2() {
		return ResponseEntity.ok(simulationService.getMinPO2());
	}

	@PreAuthorize("hasRole('USER')")
	@GetMapping(value = "/get-max-pCO2")
	public ResponseEntity<Patient> getMaxPCO2() {
		return ResponseEntity.ok(simulationService.getMaxPCO2());

	}

	@GetMapping(value = "/patient-data/{id}")
	public ResponseEntity<PatientDataDTO> getPatientData(@PathVariable UUID id) {
		return ResponseEntity.ok(simulationService.getPatientData(id));
	}

	@PreAuthorize("hasRole('USER')")
	@GetMapping(value = "/patients-data")
	public ResponseEntity<List<PatientDataDTO>> getPatientsData() {
		return ResponseEntity.ok(simulationService.getPatientsData());
	}

}
