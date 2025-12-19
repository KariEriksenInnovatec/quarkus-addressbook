package net.innovatec.adressebok.application;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import net.innovatec.adressebok.domain.AdressebokRepository;
import net.innovatec.adressebok.domain.AdressebokService;
import net.innovatec.adressebok.domain.model.Adressebok;
import net.innovatec.adressebok.domain.model.AdressebokId;
import net.innovatec.adressebok.domain.model.Kontakt;
import net.innovatec.adressebok.domain.model.KontaktData;
import net.innovatec.adressebok.domain.model.KontaktId;
import net.innovatec.adressebok.domain.model.Navn;

/**
 * Tjeneste som eksponerer metoder for å oppdatere og hente ut objekt fra domene
 * modellen. Den har ansvar for å mappe til/fra DTO og domene modellene
 */

@ApplicationScoped
public class AdressebokServiceImpl implements AdressebokService {
    @Inject
    private AdressebokRepository adressebokRepo;

    public AdressebokId opprettAdressebok() {
        Adressebok bok = adressebokRepo.opprettAdressebok();
        return bok.hentId();
    }

    public AdressebokId lagreAdressebok(Adressebok bok) {
        return adressebokRepo.lagreAdressebok(bok);
    }

    public Adressebok hentAdressebok(AdressebokId id) {
        Adressebok bok = adressebokRepo.hentAdressebok(id);
        return bok;
    }

    public void slettAdressebok(AdressebokId id) {
        adressebokRepo.slettAdressebok(id);
    }

    public KontaktId opprettOgLeggTilKontakt(AdressebokId id, Navn navn) {
        Adressebok bok = hentAdressebok(id);
        Kontakt nyKontakt = Kontakt.createNewKontakt(navn);
        bok.leggTilKontakt(nyKontakt);
        adressebokRepo.lagreAdressebok(bok);
        return nyKontakt.hentId();
    }

    public KontaktId opprettOgLeggTilKontakt(AdressebokId adressebokId, KontaktData data) {
        Adressebok bok = hentAdressebok(adressebokId);
        Kontakt nyKontakt = Kontakt.createNewKontakt(data);
        bok.leggTilKontakt(nyKontakt);
        adressebokRepo.lagreAdressebok(bok);
        return nyKontakt.hentId();
    }

    public Kontakt hentKontakt(AdressebokId adressebokId, KontaktId kontaktId) {
        Adressebok bok = hentAdressebok(adressebokId);
        Kontakt kontakt = bok.henteKontakt(kontaktId);
        return kontakt;
    }

    public void slettKontakt(AdressebokId adressebokId, KontaktId kontaktId) {
        Adressebok bok = hentAdressebok(adressebokId);
        bok.slettKontakt(kontaktId);
        adressebokRepo.lagreAdressebok(bok);
    }

    public List<Kontakt> søkKontakt(AdressebokId adressebokId, String kriterie) {
        Adressebok bok = hentAdressebok(adressebokId);
        List<Kontakt> resultat = bok.søkKontakt(kriterie);
        return resultat;
    }
}
