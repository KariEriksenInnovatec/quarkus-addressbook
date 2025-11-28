package net.innovatec.adressebok.infrastructure.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {

    @Override
    public Response toResponse(IllegalArgumentException exception) {
        ProblemDetails problemDetails = new ProblemDetails(
            "https://www.innovatec.net/adressebok/ugyldig-parameter-feil",
            "Illegal argument",
            Response.Status.BAD_REQUEST.getStatusCode(),
            exception.getMessage(),
            "" // You can set the instance URI if applicable
        );

        return Response.status(Response.Status.BAD_REQUEST)
                       .entity(problemDetails)
                       .type("application/problem+json")
                       .build();
    }
}
