package net.innovatec.adressebok.infrastructure.exception;

import java.util.Optional;

import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.core.Response.Status;
import net.innovatec.adressebok.domain.model.AdressebokId;
import net.innovatec.adressebok.domain.model.DomeneException;
import net.innovatec.adressebok.domain.model.KontaktId;

public abstract class ApplicationException extends ClientErrorException {
    private static final long serialVersionUID = 7994300787885353725L;
    private final Optional<AdressebokId> adressebokId;
    private final Optional<KontaktId> kontaktId;

    public ApplicationException(String message, Status status, DomeneException cause) {
        super(message, status, cause);
        this.adressebokId = cause.getAdressebokId();
        this.kontaktId = cause.getKontaktId();
    }

    public ApplicationException(String message, Status status, Optional<AdressebokId> adressebokId, Optional<KontaktId> kontaktId,
            DomeneException cause) {
        super(message, status, cause);
        this.adressebokId = cause.getAdressebokId().isPresent() ? cause.getAdressebokId() : adressebokId;
        this.kontaktId = cause.getKontaktId().isPresent() ? cause.getKontaktId() : kontaktId;
    }

    public ApplicationException(String message, Status status, Optional<AdressebokId> adressebokId, Optional<KontaktId> kontaktId) {
        super(message, status);
        this.adressebokId = adressebokId;
        this.kontaktId = kontaktId;
    }

    public ApplicationException(String message, Status status, Optional<AdressebokId> adressebokId, Optional<KontaktId> kontaktId,
            Throwable cause) {
        super(message, status);
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
