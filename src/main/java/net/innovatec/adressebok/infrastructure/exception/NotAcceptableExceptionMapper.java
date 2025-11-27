package net.innovatec.adressebok.infrastructure.exception;

import jakarta.ws.rs.NotAcceptableException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotAcceptableExceptionMapper implements ExceptionMapper<NotAcceptableException> {

    @Override
    public Response toResponse(NotAcceptableException exception) {
        return Response.status(Response.Status.NOT_ACCEPTABLE)
                .entity("Endest application/json som blir returnert.")
                .build();
    }
}
