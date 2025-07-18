package com.ftn.sbnz.model.models;

import java.util.UUID;

public class Patient {
    private UUID id;
    private String name;
    private Double weight;
    private Boolean conscious;
    private Double pO2;
    private Double pCO2;
    private Double participationPercentage;
    private Double tidalVolume;
    private Integer frequency;
    private Double minuteVolume;
    private Double pressure;
    private Double resistance;
    private Double gasFlow;
    private Double compliance;
    private Double FiO2;
    private String respiratorMode;
    private Boolean volumeControlled;

    public Patient() {
        this.id = UUID.randomUUID();
    }

    public Patient(
            String name, Double weight,
            Boolean conscious,
            Double pO2,
            Double pCO2,
            Double participationPercentage,
            Double tidalVolume,
            Integer frequency,
            Double minuteVolume,
            Double pressure,
            Double resistance,
            Double gasFlow,
            Double compliance,
            Double fiO2,
            String respiratorMode,
            Boolean volumeControlled
    ) {
        this.name = name;
        this.respiratorMode = respiratorMode;
        this.volumeControlled = volumeControlled;
        this.id = UUID.randomUUID();
        this.conscious = conscious;
        this.weight = weight;
        this.pO2 = pO2;
        this.pCO2 = pCO2;
        this.participationPercentage = participationPercentage;
        this.tidalVolume = tidalVolume;
        this.frequency = frequency;
        this.minuteVolume = minuteVolume;
        this.pressure = pressure;
        this.resistance = resistance;
        this.gasFlow = gasFlow;
        this.compliance = compliance;
        this.FiO2 = fiO2;
    }

    // region Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public Double getpO2() {
        return pO2;
    }

    public void setpO2(Double pO2) {
        this.pO2 = pO2;
    }

    public Double getpCO2() {
        return pCO2;
    }

    public void setpCO2(Double pCO2) {
        this.pCO2 = pCO2;
    }

    public Double getParticipationPercentage() {
        return participationPercentage;
    }

    public void setParticipationPercentage(Double participationPercentage) {
        this.participationPercentage = participationPercentage;
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

    public String getRespiratorMode() {
        return respiratorMode;
    }

    public void setRespiratorMode(String respiratorMode) {
        this.respiratorMode = respiratorMode;
    }

    public Boolean getVolumeControlled() {
        return volumeControlled;
    }

    public void setVolumeControlled(Boolean volumeControlled) {
        this.volumeControlled = volumeControlled;
    }

    // endregion

    @Override
    public String toString() {
        return "Patient{" +
                "\n id=" + id +
                "\n name=" + name +
                "\n weight=" + weight +
                "\n conscious=" + conscious +
                "\n pO2=" + pO2 +
                "\n pCO2=" + pCO2 +
                "\n participationPercentage=" + participationPercentage +
                "\n tidalVolume=" + tidalVolume +
                "\n frequency=" + frequency +
                "\n minuteVolume=" + minuteVolume +
                "\n pressure=" + pressure +
                "\n resistance=" + resistance +
                "\n gasFlow=" + gasFlow +
                "\n compliance=" + compliance +
                "\n FiO2=" + FiO2 +
                "\n}";
    }
}

