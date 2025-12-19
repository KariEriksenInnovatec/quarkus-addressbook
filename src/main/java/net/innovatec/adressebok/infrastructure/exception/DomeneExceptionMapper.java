package net.innovatec.adressebok.infrastructure.exception;

import java.util.Optional;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import net.innovatec.adressebok.domain.model.AdressebokId;
import net.innovatec.adressebok.domain.model.DomeneException;
import net.innovatec.adressebok.domain.model.IkkeFunnetDomeneException;
import net.innovatec.adressebok.domain.model.KontaktId;

@Provider
public class DomeneExceptionMapper implements ExceptionMapper<DomeneException> {

    @Override
    public Response toResponse(DomeneException exception) {
        String instanceUri = null;
        ProblemDetails problemDetails = null;
        Status status = Response.Status.BAD_REQUEST;

        Optional<AdressebokId> adressebokId = exception.getAdressebokId().isPresent() ? exception.getAdressebokId()
                : exception.getAdressebokId();
        Optional<KontaktId> kontaktId = exception.getKontaktId().isPresent() ? exception.getKontaktId()
                : exception.getKontaktId();
        
        if (exception instanceof IkkeFunnetDomeneException) {
            status = Response.Status.NOT_FOUND;
        } else {
            status = Response.Status.BAD_REQUEST;
        }

        instanceUri = buildInstanceUri(adressebokId, kontaktId);
        problemDetails = new ProblemDetails("https://www.innovatec.net/adressebok/domene-feil", "Domene Feil",
                status.getStatusCode(), exception.getMessage(), instanceUri);

        return Response.status(status).entity(problemDetails).type("application/problem+json")
                .build();
    }

    private String buildInstanceUri(Optional<AdressebokId> adressebokId, Optional<KontaktId> kontaktId) {
        StringBuilder uriBuilder = new StringBuilder("/adressebok/");
        if (adressebokId.isPresent()) {
            uriBuilder.append(adressebokId.get().getId());
        }
        if (kontaktId.isPresent()) {
            uriBuilder.append("/kontakt/").append(kontaktId.get().getId());
        }
        return uriBuilder.toString();
    }
}
