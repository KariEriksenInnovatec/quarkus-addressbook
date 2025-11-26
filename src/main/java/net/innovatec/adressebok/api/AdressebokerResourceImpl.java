package net.innovatec.adressebok.api;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import net.innovatec.adressebok.AdressebokerResource;
import net.innovatec.adressebok.beans.AdressebokMedKontakterResponse;
import net.innovatec.adressebok.beans.AdressebokResponse;
import net.innovatec.adressebok.beans.KontaktResponse;
import net.innovatec.adressebok.beans.OppdaterKontaktRequest;
import net.innovatec.adressebok.beans.OpprettKontaktRequest;
import net.innovatec.adressebok.domain.AdressebokService;
import net.innovatec.adressebok.domain.model.AdressebokId;

public class AdressebokerResourceImpl implements AdressebokerResource {

    @Inject
    AdressebokService service;

    @Override
    public List<AdressebokResponse> hentAlleAdresseboker() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hentAlleAdresseboker'");
    }

    @Override
    public AdressebokResponse opprettAdressebok() {
        
        AdressebokId adressebokId = service.opprettAdressebok();

        AdressebokResponse response = new AdressebokResponse();
        response.setId(adressebokId.adressebokId());
        response.setAntallKontakter(0);

        return response;
    }

    @Override
    public AdressebokMedKontakterResponse hentAdressebok(String adressebokId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hentAdressebok'");
    }

    @Override
    public void slettAdressebok(String adressebokId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'slettAdressebok'");
    }

    @Override
    public List<KontaktResponse> hentKontakter(String adressebokId, @Size(min = 1) String sok) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hentKontakter'");
    }

    @Override
    public KontaktResponse opprettKontakt(String adressebokId, @NotNull OpprettKontaktRequest data) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'opprettKontakt'");
    }

    @Override
    public KontaktResponse hentKontakt(String adressebokId, String kontaktId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hentKontakt'");
    }

    @Override
    public KontaktResponse oppdaterKontakt(String adressebokId, String kontaktId,
            @NotNull OppdaterKontaktRequest data) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'oppdaterKontakt'");
    }

    @Override
    public void slettKontakt(String adressebokId, String kontaktId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'slettKontakt'");
    }

}
