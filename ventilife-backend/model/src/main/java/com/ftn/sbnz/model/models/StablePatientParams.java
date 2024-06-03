package com.ftn.sbnz.model.models;

import java.util.UUID;

public class StablePatientParams {
    private UUID patientId;
    private Double tidalVolume;
    private Double minuteVolume;
    private Double pressure;
    private Double gasFlow;

    public StablePatientParams() {
    }

    public StablePatientParams(
            UUID patientId,
            Double tidalVolume,
            Double minuteVolume,
            Double pressure,
            Double gasFlow) {
        this.patientId = patientId;
        this.tidalVolume = tidalVolume;
        this.minuteVolume = minuteVolume;
        this.pressure = pressure;
        this.gasFlow = gasFlow;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public Double getTidalVolume() {
        return tidalVolume;
    }

    public void setTidalVolume(Double tidalVolume) {
        this.tidalVolume = tidalVolume;
    }

    public Double getMinuteVolume() {
        return minuteVolume;
    }

    public void setMinuteVolume(Double minuteVolume) {
        this.minuteVolume = minuteVolume;
    }

    public Double getGasFlow() {
        return gasFlow;
    }

    public void setGasFlow(Double gasFlow) {
        this.gasFlow = gasFlow;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }
}
