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
public class ApplicationExceptionMapper implements ExceptionMapper<ApplicationException> {

    @Override
    public Response toResponse(ApplicationException exception) {
        String instanceUri = null;
        ProblemDetails problemDetails = null;
        Status status = Response.Status.BAD_REQUEST;

        if (exception.getCause() instanceof DomeneException) {
            DomeneException cause = (DomeneException) exception.getCause();
            Optional<AdressebokId> adressebokId = cause.getAdressebokId().isPresent() ? cause.getAdressebokId()
                    : exception.getAdressebokId();
            Optional<KontaktId> kontaktId = cause.getKontaktId().isPresent() ? cause.getKontaktId()
                    : exception.getKontaktId();


            if (exception.getCause() instanceof IkkeFunnetDomeneException) {
                status = Response.Status.NOT_FOUND;
            } else {
                status = Response.Status.BAD_REQUEST;
            }

            instanceUri = buildInstanceUri(adressebokId, kontaktId);
            problemDetails = new ProblemDetails("https://www.innovatec.net/adressebok/domene-feil", "Domene Feil",
                    status.getStatusCode(), exception.getCause().getMessage(), instanceUri);

        } else {
            instanceUri = buildInstanceUri(exception.getAdressebokId(), exception.getKontaktId());
            problemDetails = new ProblemDetails("https://www.innovatec.net/adressebok/applikasjon-feil",
                    "Applikasjon Feil", Response.Status.BAD_REQUEST.getStatusCode(), exception.getMessage(),
                    instanceUri);
        }

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
