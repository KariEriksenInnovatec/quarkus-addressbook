package net.innovatec.adressebok.application;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import net.innovatec.adressebok.domain.AdressebokRepo;
import net.innovatec.adressebok.domain.AdressebokService;
import net.innovatec.adressebok.domain.model.AdresseType;
import net.innovatec.adressebok.domain.model.Adressebok;
import net.innovatec.adressebok.domain.model.Kontakt;
import net.innovatec.adressebok.domain.model.TelefonType;

@ApplicationScoped
public class AdressebokServiceImpl implements AdressebokService {

    // Orchestrerer operasjoner
    // Kaller Repo som gjør det faktiske arbeidet

    @Inject
    AdressebokRepo repo;

    @Override
    public List<Adressebok> hentAlleAdresseboker() {
        return repo.hentAlleAdresseboker();
    }

    @Override
    public Adressebok opprettAdressebok() {
        return repo.opprettAdressebok();
    }

    @Override
    public Adressebok hentAdressebok(String adressebokId) {
        return repo.hentAdressebok(adressebokId);
    }

    @Override
    public void slettAdressebok(String adressebokId) {
        repo.slettAdressebok(adressebokId);
    }

    @Override
    public List<Kontakt> hentKontakter(String adressebokId) {
        return repo.hentKontakter(adressebokId);
    }

    @Override
    public Kontakt opprettKontakt(String adressebokId, String fornavn, String etternavn) {
        return repo.opprettKontakt(adressebokId, fornavn, etternavn);
    }

    @Override
    public Kontakt hentKontakt(String adressebokId, String kontaktId) {
        return repo.hentKontakt(adressebokId, kontaktId);
    }

    @Override
    public Kontakt oppdaterKontakt(String adressebokId, String kontaktId, AdresseType adresseType, String gatenavn,
            String gatenummer, String postnummer,
            String by,
            String land, String epostAdresse, TelefonType telefonType, String telefonnummer) {
        return repo.oppdaterKontakt(adressebokId, kontaktId, adresseType, gatenavn, gatenummer, postnummer, by, land,
                epostAdresse, telefonType, telefonnummer);
    }

    @Override
    public void slettKontakt(String adressebokId, String kontaktId) {
        repo.slettKontakt(adressebokId, kontaktId);
    }
}
