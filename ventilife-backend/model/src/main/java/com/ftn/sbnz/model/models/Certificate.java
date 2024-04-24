package com.ftn.sbnz.model.models;

import org.kie.api.definition.type.Position;

public class Certificate {
    @Position(0)
    String certificate;

    @Position(1)
    String rootCertificate;

    @Position(2)
    String valid;

    public Certificate() {
    }

    public Certificate(String certificate, String rootCertificate, String valid) {
        this.certificate = certificate;
        this.rootCertificate = rootCertificate;
        this.valid = valid;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getRootCertificate() {
        return rootCertificate;
    }

    public void setRootCertificate(String rootCertificate) {
        this.rootCertificate = rootCertificate;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }
}
