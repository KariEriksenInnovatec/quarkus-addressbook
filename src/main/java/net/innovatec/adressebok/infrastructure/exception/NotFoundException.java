package net.innovatec.adressebok.infrastructure.exception;

import java.util.Optional;

import jakarta.ws.rs.core.Response.Status;
import net.innovatec.adressebok.domain.model.AdressebokId;
import net.innovatec.adressebok.domain.model.DomeneException;
import net.innovatec.adressebok.domain.model.KontaktId;

public class NotFoundException extends ApplicationException {
    private static final long serialVersionUID = -1534580614823990386L;

    public NotFoundException(String message, DomeneException cause) {
        super(message, Status.NOT_FOUND, cause);
    }

    public NotFoundException(String message, Optional<AdressebokId> adressebokId,
            Optional<KontaktId> kontaktId, DomeneException cause) {
        super(message, Status.NOT_FOUND, adressebokId, kontaktId, cause);
    }

    public NotFoundException(String message, Optional<AdressebokId> adressebokId,
            Optional<KontaktId> kontaktId, Throwable cause) {
        super(message, Status.NOT_FOUND, adressebokId, kontaktId, cause);
    }

    public NotFoundException(String message, Optional<AdressebokId> adressebokId,
            Optional<KontaktId> kontaktId) {
        super(message, Status.NOT_FOUND, adressebokId, kontaktId);
    }

}
