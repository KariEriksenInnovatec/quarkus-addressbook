package net.innovatec.adressebok.api.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import net.innovatec.adressebok.beans.Feilmelding;
import java.util.Date;

@Provider
public class DuplikatExceptionMapper implements ExceptionMapper<DuplikatKontaktException> {
    
    @Override
    public Response toResponse(DuplikatKontaktException exception) {
        Feilmelding feilmelding = new Feilmelding();
        feilmelding.setStatus(409);
        feilmelding.setMelding(exception.getMessage());
        feilmelding.setTidspunkt(new Date());
            
        return Response.status(Response.Status.CONFLICT)
            .entity(feilmelding)
            .build();
    }
}