package com.ftn.sbnz.model.models;

import java.util.UUID;

public class Patient {
    private UUID id;
    private Double weight;
    private Boolean conscious;
    private Double pO2;
    private Double pCO2;
    private Double tidalVolume;
    private Integer frequency;
    private Double minuteVolume;
    private Double pressure;
    private Double resistance;
    private Double gasFlow;
    private Double compliance;
    private Double FiO2;

    public Patient() {
        this.id = UUID.randomUUID();
    }

    public Patient(
            Double weight,
            Boolean conscious,
            Double pO2,
            Double pCO2,
            Double tidalVolume,
            Integer frequency,
            Double minuteVolume,
            Double pressure,
            Double resistance,
            Double gasFlow,
            Double compliance,
            Double fiO2
    ) {
        this.id = UUID.randomUUID();
        this.conscious = conscious;
        this.weight = weight;
        this.pO2 = pO2;
        this.pCO2 = pCO2;
        this.tidalVolume = tidalVolume;
        this.frequency = frequency;
        this.minuteVolume = minuteVolume;
        this.pressure = pressure;
        this.resistance = resistance;
        this.gasFlow = gasFlow;
        this.compliance = compliance;
        FiO2 = fiO2;
    }

    // region Getters and Setters


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Boolean getConscious() {
        return conscious;
    }

    public void setConscious(Boolean conscious) {
        this.conscious = conscious;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getPO2() {
        return pO2;
    }

    public void setPO2(Double pO2) {
        this.pO2 = pO2;
    }

    public Double getPCO2() {
        return pCO2;
    }

    public void setPCO2(Double pCO2) {
        this.pCO2 = pCO2;
    }

    public Double getTidalVolume() {
        return tidalVolume;
    }

    public void setTidalVolume(Double tidalVolume) {
        this.tidalVolume = tidalVolume;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public Double getMinuteVolume() {
        return minuteVolume;
    }

    public void setMinuteVolume(Double minuteVolume) {
        this.minuteVolume = minuteVolume;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Double getResistance() {
        return resistance;
    }

    public void setResistance(Double resistance) {
        this.resistance = resistance;
    }

    public Double getGasFlow() {
        return gasFlow;
    }

    public void setGasFlow(Double gasFlow) {
        this.gasFlow = gasFlow;
    }

    public Double getCompliance() {
        return compliance;
    }

    public void setCompliance(Double compliance) {
        this.compliance = compliance;
    }

    public Double getFiO2() {
        return FiO2;
    }

    public void setFiO2(Double fiO2) {
        FiO2 = fiO2;
    }

    // endregion

    @Override
    public String toString() {
        return "Patient{" +
                "\n weight=" + weight +
                "\n conscious=" + conscious +
                "\n pO2=" + pO2 +
                "\n pCO2=" + pCO2 +
                "\n tidalVolume=" + tidalVolume +
                "\n frequency=" + frequency +
                "\n minuteVolume=" + minuteVolume +
                "\n pressure=" + pressure +
                "\n resistance=" + resistance +
                "\n gasFlow=" + gasFlow +
                "\n compliance=" + compliance +
                "\n FiO2=" + FiO2 +
                '}';
    }
}

