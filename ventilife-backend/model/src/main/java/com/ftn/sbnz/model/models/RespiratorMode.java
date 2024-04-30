package com.ftn.sbnz.model.models;

import org.kie.api.definition.type.Position;

public class RespiratorMode {
    @Position(0)
    private String name;

    @Position(1)
    private String group;

    @Position(2)
    private Boolean conscious;

    @Position(3)
    private Double participationPercentageLB;

    @Position(4)
    private Double participationPercentageUB;

    private Double pO2LB;
    private Double pO2UB;
    private Double pCO2LB;
    private Double pCO2UB;

    public RespiratorMode() {
    }

    public RespiratorMode(String name, String group, Boolean conscious, Double participationPercentageLB,
                          Double participationPercentageUB, Double pO2LB, Double pO2UB, Double pCO2LB, Double pCO2UB) {
        this.name = name;
        this.group = group;
        this.conscious = conscious;
        this.participationPercentageLB = participationPercentageLB;
        this.participationPercentageUB = participationPercentageUB;
        this.pO2LB = pO2LB;
        this.pO2UB = pO2UB;
        this.pCO2LB = pCO2LB;
        this.pCO2UB = pCO2UB;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Boolean getConscious() {
        return conscious;
    }

    public void setConscious(Boolean conscious) {
        this.conscious = conscious;
    }

    public Double getParticipationPercentageLB() {
        return participationPercentageLB;
    }

    public void setParticipationPercentageLB(Double participationPercentageLB) {
        this.participationPercentageLB = participationPercentageLB;
    }

    public Double getParticipationPercentageUB() {
        return participationPercentageUB;
    }

    public void setParticipationPercentageUB(Double participationPercentageUB) {
        this.participationPercentageUB = participationPercentageUB;
    }

    public Double getpO2LB() {
        return pO2LB;
    }

    public void setpO2LB(Double pO2LB) {
        this.pO2LB = pO2LB;
    }

    public Double getpO2UB() {
        return pO2UB;
    }

    public void setpO2UB(Double pO2UB) {
        this.pO2UB = pO2UB;
    }

    public Double getpCO2LB() {
        return pCO2LB;
    }

    public void setpCO2LB(Double pCO2LB) {
        this.pCO2LB = pCO2LB;
    }

    public Double getpCO2UB() {
        return pCO2UB;
    }

    public void setpCO2UB(Double pCO2UB) {
        this.pCO2UB = pCO2UB;
    }
}
