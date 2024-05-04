package com.ftn.sbnz.model.events;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

import java.util.UUID;

@Role(Role.Type.EVENT)
@Expires("30m")
public class PO2Change {
    private UUID patientId;
    private Double deltaPO2;

    public PO2Change() {

    }

    public PO2Change(UUID patientId, Double deltaPO2) {
        this.patientId = patientId;
        this.deltaPO2 = deltaPO2;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public Double getDeltaPO2() {
        return deltaPO2;
    }

    public void setDeltaPO2(Double deltaPO2) {
        this.deltaPO2 = deltaPO2;
    }
}
