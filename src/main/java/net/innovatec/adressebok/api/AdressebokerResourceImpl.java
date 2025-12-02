package net.innovatec.adressebok.api;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.ws.rs.Path;
import net.innovatec.adressebok.AdressebokerResource;
import net.innovatec.adressebok.api.mapper.AdressebokMapper;
import net.innovatec.adressebok.beans.AdressebokMedKontakterResponse;
import net.innovatec.adressebok.beans.AdressebokResponse;
import net.innovatec.adressebok.beans.KontaktResponse;
import net.innovatec.adressebok.beans.OppdaterKontaktRequest;
import net.innovatec.adressebok.beans.OpprettKontaktRequest;
import net.innovatec.adressebok.domain.AdressebokService;
import net.innovatec.adressebok.domain.model.Adressebok;
import net.innovatec.adressebok.domain.model.Kontakt;

@Path("/adresseboker")
@RequestScoped
public class AdressebokerResourceImpl implements AdressebokerResource {

    @Inject
    AdressebokService service;

    @Inject
    AdressebokMapper mapper;

    @Override
    public List<AdressebokResponse> hentAlleAdresseboker() {

        // Henter alle adressebøker fra service/repo/lagring
        List<Adressebok> adresseboker = service.hentAlleAdresseboker();

        return mapper.toResponse(adresseboker);
    }

    @Override
    public AdressebokResponse opprettAdressebok() {

        // Kaller service-laget (som implementerer hva som skjer)
        // Mapper http-data (api-format) til service-kall (domene-format)

        Adressebok adressebok = service.opprettAdressebok();

        return mapper.toResponse(adressebok);
    }

    @Override
    public AdressebokMedKontakterResponse hentAdressebok(String adressebokId) {

        // Henter adressebok fra service
        Adressebok adressebok = service.hentAdressebok(adressebokId);

        return mapper.toAdressebokMedKontaktResponse(adressebok);
    }

    @Override
    public void slettAdressebok(String adressebokId) {
        
        service.slettAdressebok(adressebokId);
    }

    @Override
    public List<KontaktResponse> hentKontakter(String adressebokId, @Size(min = 1) String sok) {

        Adressebok adressebok = service.hentAdressebok(adressebokId);
        List<Kontakt> kontakter = adressebok.hentKontakter();

        return mapper.toKontaktResponse(kontakter);
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
