package net.innovatec.adressebok.api.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import net.innovatec.adressebok.beans.Feilmelding;
import java.util.Date;

@Provider
public class UgyldigInputExceptionMapper implements ExceptionMapper<UgyldigInputException> {
    
    @Override
    public Response toResponse(UgyldigInputException exception) {
        Feilmelding feilmelding = new Feilmelding();
        feilmelding.setStatus(400);
        feilmelding.setMelding(exception.getMessage());
        feilmelding.setTidspunkt(new Date());
            
        return Response.status(Response.Status.BAD_REQUEST)
            .entity(feilmelding)
            .build();
    }
}