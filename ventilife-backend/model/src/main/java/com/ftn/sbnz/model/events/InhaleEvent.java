package com.ftn.sbnz.model.events;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

import java.util.UUID;

@Role(Role.Type.EVENT)
@Expires("30m")
public class InhaleEvent {
    private UUID patientId;
    private Double inhaledVolume;

    public InhaleEvent() {
    }

    public InhaleEvent(UUID patientId, Double inhaledVolume) {
        this.patientId = patientId;
        this.inhaledVolume = inhaledVolume;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public Double getInhaledVolume() {
        return inhaledVolume;
    }

    public void setInhaledVolume(Double inhaledVolume) {
        this.inhaledVolume = inhaledVolume;
    }
}
