package net.innovatec.adressebok.domain.model;

import java.util.Optional;

public class DomeneException extends RuntimeException {
    private static final long serialVersionUID = 2948122371941721525L;
    private final Optional<AdressebokId> adressebokId;
    private final Optional<KontaktId> kontaktId;

    public DomeneException(String melding) {
        super(melding);
        this.adressebokId = Optional.empty();
        this.kontaktId = Optional.empty();
    }

    public DomeneException(String melding, Optional<AdressebokId> adressebokId, Optional<KontaktId> kontaktId) {
        super(melding);
        this.adressebokId = adressebokId;
        this.kontaktId = kontaktId;
    }

    public Optional<AdressebokId> getAdressebokId() {
        return adressebokId;
    }

    public Optional<KontaktId> getKontaktId() {
        return kontaktId;
    }

}
