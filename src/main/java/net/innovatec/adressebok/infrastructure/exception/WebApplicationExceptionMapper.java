package net.innovatec.adressebok.infrastructure.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {

    @Override
    public Response toResponse(WebApplicationException exception) {
        ProblemDetails problemDetails = new ProblemDetails(
            "https://www.innovatec.net/adressebok/runtime-feil",
            "Web Applikasjon Feil",
            exception.getResponse().getStatus(),
            exception.getCause() != null ? exception.getCause().getMessage() : exception.getMessage(),
            "" // You can set the instance URI if applicable
        );

        return Response.status(exception.getResponse().getStatus())
                       .entity(problemDetails)
                       .type("application/problem+json")
                       .build();
    }
}
