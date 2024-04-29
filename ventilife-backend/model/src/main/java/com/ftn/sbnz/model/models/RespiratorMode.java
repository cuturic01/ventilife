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

    public RespiratorMode() {
    }

    public RespiratorMode(String name, String group, Boolean conscious, Double participationPercentageLB, Double participationPercentageUB) {
        this.name = name;
        this.group = group;
        this.conscious = conscious;
        this.participationPercentageLB = participationPercentageLB;
        this.participationPercentageUB = participationPercentageUB;
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
}
