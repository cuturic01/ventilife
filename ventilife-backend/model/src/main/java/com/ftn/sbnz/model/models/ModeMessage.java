package com.ftn.sbnz.model.models;

import java.util.UUID;

public class ModeMessage {
    private UUID patientId;
    private String modeConfirmation;
    private String recommendedMode;

    public ModeMessage() {
    }

    public ModeMessage(UUID patientId, String modeConfirmation, String recommendedMode) {
        this.patientId = patientId;
        this.modeConfirmation = modeConfirmation;
        this.recommendedMode = recommendedMode;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public String getModeConfirmation() {
        return modeConfirmation;
    }

    public void setModeConfirmation(String modeConfirmation) {
        this.modeConfirmation = modeConfirmation;
    }

    public String getRecommendedMode() {
        return recommendedMode;
    }

    public void setRecommendedMode(String recommendedMode) {
        this.recommendedMode = recommendedMode;
    }
}
