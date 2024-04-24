package com.ftn.sbnz.model.models;

import java.util.UUID;

public class RespiratorDecision {
    private UUID patientId;
    private Boolean frequencyDecision;
    private Boolean tidalVolumeDecision;
    private Boolean pCO2Decision;
    private Boolean pO2Decision;

    private Boolean finalDecision;

    public RespiratorDecision() {
        frequencyDecision = false;
        tidalVolumeDecision = false;
        pCO2Decision = false;
        pO2Decision = false;
        finalDecision = false;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public Boolean getFrequencyDecision() {
        return frequencyDecision;
    }

    public void setFrequencyDecision(Boolean frequencyDecision) {
        this.frequencyDecision = frequencyDecision;
    }

    public Boolean getTidalVolumeDecision() {
        return tidalVolumeDecision;
    }

    public void setTidalVolumeDecision(Boolean tidalVolumeDecision) {
        this.tidalVolumeDecision = tidalVolumeDecision;
    }

    public Boolean getPCO2Decision() {
        return pCO2Decision;
    }

    public void setPCO2Decision(Boolean pCO2Decision) {
        this.pCO2Decision = pCO2Decision;
    }

    public Boolean getPO2Decision() {
        return pO2Decision;
    }

    public void setPO2Decision(Boolean pO2Decision) {
        this.pO2Decision = pO2Decision;
    }

    public Boolean getFinalDecision() {
        return finalDecision;
    }

    public void setFinalDecision(Boolean finalDecision) {
        this.finalDecision = finalDecision;
    }

    @Override
    public String toString() {
        return "RespiratorDecision{" +
                "patientId=" + patientId +
                ", frequencyDecision=" + frequencyDecision +
                ", tidalVolumeDecision=" + tidalVolumeDecision +
                ", pCO2Decision=" + pCO2Decision +
                ", pO2Decision=" + pO2Decision +
                ", finalDecision=" + finalDecision +
                '}';
    }
}
