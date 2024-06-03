package com.ftn.sbnz.model.models;

public class Thresholds {
    private Double deltaPO2Threshold; // -1.5
    private Double deltaPCO2Threshold; // 0.5
    private Double deltaParticipationPercentageThreshold; // 25

    public Thresholds() {
    }

    public Thresholds(Double deltaPO2Threshold, Double deltaPCO2Threshold, Double deltaParticipationPercentageThreshold) {
        this.deltaPO2Threshold = deltaPO2Threshold;
        this.deltaPCO2Threshold = deltaPCO2Threshold;
        this.deltaParticipationPercentageThreshold = deltaParticipationPercentageThreshold;
    }

    public Double getDeltaPO2Threshold() {
        return deltaPO2Threshold;
    }

    public void setDeltaPO2Threshold(Double deltaPO2Threshold) {
        this.deltaPO2Threshold = deltaPO2Threshold;
    }

    public Double getDeltaPCO2Threshold() {
        return deltaPCO2Threshold;
    }

    public void setDeltaPCO2Threshold(Double deltaPCO2Threshold) {
        this.deltaPCO2Threshold = deltaPCO2Threshold;
    }

    public Double getDeltaParticipationPercentageThreshold() {
        return deltaParticipationPercentageThreshold;
    }

    public void setDeltaParticipationPercentageThreshold(Double deltaParticipationPercentageThreshold) {
        this.deltaParticipationPercentageThreshold = deltaParticipationPercentageThreshold;
    }
}
