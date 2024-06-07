package com.ftn.sbnz.service.dto;

import com.ftn.sbnz.model.models.ChangeRecord;
import com.ftn.sbnz.model.models.Patient;
import com.ftn.sbnz.model.models.RespiratorDecision;
import com.ftn.sbnz.model.models.StablePatientParams;

public class PatientDataDTO {
    private Patient patient;
    private StablePatientParams stablePatientParams;
    private ChangeRecord changeRecord;
    private RespiratorDecision respiratorDecision;

    public PatientDataDTO() {

    }

    public PatientDataDTO(Patient patient, StablePatientParams stablePatientParams, ChangeRecord changeRecord, RespiratorDecision respiratorDecision) {
        this.patient = patient;
        this.stablePatientParams = stablePatientParams;
        this.changeRecord = changeRecord;
        this.respiratorDecision = respiratorDecision;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public StablePatientParams getStablePatientParams() {
        return stablePatientParams;
    }

    public void setStablePatientParams(StablePatientParams stablePatientParams) {
        this.stablePatientParams = stablePatientParams;
    }

    public ChangeRecord getChangeRecord() {
        return changeRecord;
    }

    public void setChangeRecord(ChangeRecord changeRecord) {
        this.changeRecord = changeRecord;
    }

    public RespiratorDecision getRespiratorDecision() {
        return respiratorDecision;
    }

    public void setRespiratorDecision(RespiratorDecision respiratorDecision) {
        this.respiratorDecision = respiratorDecision;
    }
}
