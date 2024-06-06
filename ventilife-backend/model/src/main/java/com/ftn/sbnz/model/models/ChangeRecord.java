package com.ftn.sbnz.model.models;

import java.util.UUID;

public class ChangeRecord {
    private UUID patientId;
    private Double deltaPO2;
    private Double deltaPCO2;
    private Double participationPercentage;

    private String chosenMode;

    public ChangeRecord(UUID patientId, Double deltaPO2, Double deltaPCO2, Double participationPercentage, String chosenMode) {
        this.patientId = patientId;
        this.deltaPO2 = deltaPO2;
        this.deltaPCO2 = deltaPCO2;
        this.participationPercentage = participationPercentage;
        this.chosenMode = chosenMode;
    }

    public ChangeRecord() {}

    public ChangeRecord(UUID patientId, Double deltaPO2, Double deltaPCO2, Double deltaParticipationPercentage) {
        this.patientId = patientId;
        this.deltaPO2 = deltaPO2;
        this.deltaPCO2 = deltaPCO2;
        this.participationPercentage = deltaParticipationPercentage;
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

    public Double getParticipationPercentage() {
        return participationPercentage;
    }

    public void setParticipationPercentage(Double deltaParticipationPercentage) {
        this.participationPercentage = deltaParticipationPercentage;
    }

    @Override
    public String toString() {
        return "ChangeRecord{" +
                "patientId=" + patientId +
                ", deltaPO2=" + deltaPO2 +
                ", deltaPCO2=" + deltaPCO2 +
                ", participationPercentage=" + participationPercentage +
                ", chosenMode='" + chosenMode + '\'' +
                '}';
    }

    public String getChosenMode() {
        return chosenMode;
    }

    public void setChosenMode(String chosenMode) {
        this.chosenMode = chosenMode;
    }
}
