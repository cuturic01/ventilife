package com.ftn.sbnz.model.events;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

import java.util.UUID;

@Role(Role.Type.EVENT)
@Expires("30m")
public class ChangeEvent {
    private UUID patientId;
    private Double deltaPO2;
    private Double deltaPCO2;
    private Double deltaParticipationPercentage;

    public ChangeEvent() {
    }

    public ChangeEvent(
            UUID patientId,
            Double deltaPO2,
            Double deltaPCO2,
            Double participationPercentage) {
        this.patientId = patientId;
        this.deltaPO2 = deltaPO2;
        this.deltaPCO2 = deltaPCO2;
        this.deltaParticipationPercentage = participationPercentage;
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

    public Double getDeltaPCO2() {
        return deltaPCO2;
    }

    public void setDeltaPCO2(Double deltaPCO2) {
        this.deltaPCO2 = deltaPCO2;
    }

    public Double getDeltaParticipationPercentage() {
        return deltaParticipationPercentage;
    }

    public void setDeltaParticipationPercentage(Double deltaParticipationPercentage) {
        this.deltaParticipationPercentage = deltaParticipationPercentage;
    }
}
