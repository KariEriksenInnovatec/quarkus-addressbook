package net.innovatec.adressebok.application;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import net.innovatec.adressebok.domain.model.Adressebok;
import net.innovatec.adressebok.domain.model.AdressebokId;
import net.innovatec.adressebok.domain.model.Kontakt;
import net.innovatec.adressebok.domain.model.KontaktData;
import net.innovatec.adressebok.domain.model.KontaktId;
import net.innovatec.adressebok.domain.model.Navn;
import net.innovatec.adressebok.infrastructure.persistence.AdressebokRepository;

/**
 * Tjeneste som ekspoenerer metoder for å oppdatere og hente ut objekt fra
 * domene modellen. Den har ansvar for å mappe til/fra DTO og domene modellene
 */

@ApplicationScoped
public class AdressebokService {
    @Inject
    AdressebokRepository adressebokRepo;

    public AdressebokId opprettAdressbok() {
        Adressebok bok = adressebokRepo.opprettAdressBok();
        return bok.getId();
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
        Kontakt nyKontakt = new Kontakt(navn);
        bok.leggTilKontakt(nyKontakt);
        return nyKontakt.hentId();
    }

    public KontaktId opprettOgLeggTilKontakt(AdressebokId adressebokId, KontaktData data) {
        Adressebok bok = hentAdressebok(adressebokId);
        Kontakt nyKontakt = new Kontakt(data);
        bok.leggTilKontakt(nyKontakt);
        return nyKontakt.hentId();
    }

    public Kontakt hentKontakt(AdressebokId bokId, KontaktId kontaktId) {
        Adressebok bok = hentAdressebok(bokId);
        Kontakt kontakt = bok.henteKontakt(kontaktId);
        return kontakt;

    }

    public void slettKontakt(AdressebokId bokId, KontaktId kontaktId) {
        Adressebok bok = hentAdressebok(bokId);
        bok.slettKontakt(kontaktId);
    }
}
