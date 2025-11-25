package net.innovatec.adressebok.infrastructure.exception;

import java.util.Optional;

import jakarta.ws.rs.core.Response.Status;
import net.innovatec.adressebok.domain.model.AdressebokId;
import net.innovatec.adressebok.domain.model.DomeneException;
import net.innovatec.adressebok.domain.model.KontaktId;

public class BadRequestException extends ApplicationException {
    private static final long serialVersionUID = 2436731455709313901L;

    public BadRequestException(String message, DomeneException cause) {
        super(message, Status.BAD_REQUEST, cause);
    }

    public BadRequestException(String message, Optional<AdressebokId> adressebokId,
            Optional<KontaktId> kontaktId, DomeneException cause) {
        super(message, Status.BAD_REQUEST, adressebokId, kontaktId, cause);
    }

    public BadRequestException(String message, Optional<AdressebokId> adressebokId,
            Optional<KontaktId> kontaktId, Throwable cause) {
        super(message, Status.BAD_REQUEST, adressebokId, kontaktId, cause);
    }

    public BadRequestException(String message, Optional<AdressebokId> adressebokId,
            Optional<KontaktId> kontaktId) {
        super(message, Status.BAD_REQUEST, adressebokId, kontaktId);
    }
}
