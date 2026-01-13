package net.innovatec.adressebok.api.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import net.innovatec.adressebok.beans.Feilmelding;
import java.util.Date;

@Provider
public class IkkeFunnetExceptionMapper implements ExceptionMapper<IkkeFunnetException> {
    
    @Override
    public Response toResponse(IkkeFunnetException exception) {
        Feilmelding feilmelding = new Feilmelding();
        feilmelding.setStatus(404);
        feilmelding.setMelding(exception.getMessage());
        feilmelding.setTidspunkt(new Date());
            
        return Response.status(Response.Status.NOT_FOUND)
            .entity(feilmelding)
            .build();
    }
}