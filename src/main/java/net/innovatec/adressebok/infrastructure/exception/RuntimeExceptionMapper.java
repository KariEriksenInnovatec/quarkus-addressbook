package net.innovatec.adressebok.infrastructure.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {

    @Override
    public Response toResponse(RuntimeException exception) {
        ProblemDetails problemDetails = new ProblemDetails(
            "https://www.innovatec.net/adressebok/runtime-feil",
            "Runtime Feil",
            Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
            exception.getMessage(),
            "" // You can set the instance URI if applicable
        );

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                       .entity(problemDetails)
                       .type("application/problem+json")
                       .build();
    }
}
