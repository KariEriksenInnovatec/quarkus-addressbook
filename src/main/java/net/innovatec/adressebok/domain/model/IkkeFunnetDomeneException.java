package net.innovatec.adressebok.domain.model;

import java.util.Optional;

public class IkkeFunnetDomeneException extends DomeneException {
    private static final long serialVersionUID = 1547773167851428149L;

    public IkkeFunnetDomeneException(String melding) {
        super(melding);
    }

    public IkkeFunnetDomeneException(String melding, Optional<AdressebokId> adressebokId,
            Optional<KontaktId> kontaktId) {
        super(melding, adressebokId, kontaktId);
    }

}
