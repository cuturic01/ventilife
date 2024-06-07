package com.ftn.sbnz.service.dto;

import com.ftn.sbnz.model.models.ChangeRecord;
import com.ftn.sbnz.model.models.Patient;
import com.ftn.sbnz.model.models.ResponseMessage;

public class PatientResponseDTO {
    private Patient patient;
    private ResponseMessage responseMessage;

    private ChangeRecord changeRecord;

    public PatientResponseDTO() {
    }

    public PatientResponseDTO(Patient patient, ResponseMessage responseMessage, ChangeRecord changeRecord) {
        this.patient = patient;
        this.responseMessage = responseMessage;
        this.changeRecord = changeRecord;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public ResponseMessage getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(ResponseMessage responseMessage) {
        this.responseMessage = responseMessage;
    }

    public ChangeRecord getChangeRecord() {
        return changeRecord;
    }

    public void setChangeRecord(ChangeRecord changeRecord) {
        this.changeRecord = changeRecord;
    }
}
