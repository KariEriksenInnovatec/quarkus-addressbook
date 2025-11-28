package net.innovatec.adressebok.application;

import java.util.List;
import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response.Status;
import net.innovatec.adressebok.domain.AdressebokRepository;
import net.innovatec.adressebok.domain.AdressebokService;
import net.innovatec.adressebok.domain.model.Adressebok;
import net.innovatec.adressebok.domain.model.AdressebokId;
import net.innovatec.adressebok.domain.model.DomeneException;
import net.innovatec.adressebok.domain.model.IkkeFunnetDomeneException;
import net.innovatec.adressebok.domain.model.Kontakt;
import net.innovatec.adressebok.domain.model.KontaktData;
import net.innovatec.adressebok.domain.model.KontaktId;
import net.innovatec.adressebok.domain.model.Navn;
import net.innovatec.adressebok.infrastructure.exception.ApplicationException;
import net.innovatec.adressebok.infrastructure.exception.BadRequestException;
import net.innovatec.adressebok.infrastructure.exception.NotFoundException;

/**
 * Tjeneste som eksponerer metoder for å oppdatere og hente ut objekt fra domene
 * modellen. Den har ansvar for å mappe til/fra DTO og domene modellene
 */

@ApplicationScoped
public class AdressebokServiceImpl implements AdressebokService {
    @Inject
    private AdressebokRepository adressebokRepo;

    public AdressebokId opprettAdressebok() {
        Adressebok bok = adressebokRepo.opprettAdressBok();
        return bok.hentId();
    }

    public AdressebokId importerAdressebok(Adressebok bok) {
        try {
            return adressebokRepo.leggTilAdressebok(bok);
        } catch (DomeneException dme) {
            throw createApplicationException(Status.BAD_REQUEST, "Domene feil", bok.hentId(), null, dme);
        }
    }

    public Adressebok hentAdressebok(AdressebokId id) {
        try {
            Adressebok bok = adressebokRepo.hentAdressebok(id);
            return bok;
        } catch (IkkeFunnetDomeneException nfe) {
            throw createApplicationException(Status.NOT_FOUND, "Domene feil", id, null, nfe);
        } catch (DomeneException dme) {
            throw createApplicationException(Status.BAD_REQUEST, "Domene feil", id, null, dme);
        }
    }

    public void slettAdressebok(AdressebokId id) {
        try {
            adressebokRepo.slettAdressebok(id);
        } catch (IkkeFunnetDomeneException nfe) {
            throw createApplicationException(Status.NOT_FOUND, "Domene feil", id, null, nfe);
        } catch (DomeneException dme) {
            throw createApplicationException(Status.BAD_REQUEST, "Domene feil", id, null, dme);
        }
    }

    public KontaktId opprettOgLeggTilKontakt(AdressebokId id, Navn navn) {
        try {
            Adressebok bok = hentAdressebok(id);
            Kontakt nyKontakt = Kontakt.createNewKontakt(navn);
            bok.leggTilKontakt(nyKontakt);
            return nyKontakt.hentId();
        } catch (IkkeFunnetDomeneException nfe) {
            throw createApplicationException(Status.NOT_FOUND, "Domene feil", id, null, nfe);
        } catch (DomeneException dme) {
            throw createApplicationException(Status.BAD_REQUEST, "Domene feil", id, null, dme);
        }
    }

    public KontaktId opprettOgLeggTilKontakt(AdressebokId adressebokId, KontaktData data) {
        try {
            Adressebok bok = hentAdressebok(adressebokId);
            Kontakt nyKontakt = Kontakt.createNewKontakt(data);
            bok.leggTilKontakt(nyKontakt);
            return nyKontakt.hentId();
        } catch (IkkeFunnetDomeneException nfe) {
            throw createApplicationException(Status.NOT_FOUND, "Domene feil", adressebokId, null, nfe);
        } catch (DomeneException dme) {
            throw createApplicationException(Status.BAD_REQUEST, "Domene feil", adressebokId, null, dme);
        }
    }

    public Kontakt hentKontakt(AdressebokId adressebokId, KontaktId kontaktId) {
        try {
            Adressebok bok = hentAdressebok(adressebokId);
            Kontakt kontakt = bok.henteKontakt(kontaktId);
            return kontakt;
        } catch (IkkeFunnetDomeneException nfe) {
            throw createApplicationException(Status.NOT_FOUND, "Domene feil", adressebokId, kontaktId, nfe);
        } catch (DomeneException dme) {
            throw createApplicationException(Status.BAD_REQUEST, "Domene feil", adressebokId, kontaktId, dme);
        }
    }

    public void slettKontakt(AdressebokId adressebokId, KontaktId kontaktId) {
        try {
            Adressebok bok = hentAdressebok(adressebokId);
            bok.slettKontakt(kontaktId);
        } catch (IkkeFunnetDomeneException nfe) {
            throw createApplicationException(Status.NOT_FOUND, "Domene feil", adressebokId, kontaktId, nfe);
        } catch (DomeneException dme) {
            throw createApplicationException(Status.BAD_REQUEST, "Domene feil", adressebokId, kontaktId, dme);
        }
    }

    public List<Kontakt> søkKontakt(AdressebokId adressebokId, String kriterie) {
        try {
            Adressebok bok = hentAdressebok(adressebokId);
            List<Kontakt> resultat = bok.søkKontakt(kriterie);
            return resultat;
        } catch (IkkeFunnetDomeneException nfe) {
            throw createApplicationException(Status.NOT_FOUND, "Domene feil", adressebokId, null, nfe);
        } catch (DomeneException dme) {
            throw createApplicationException(Status.BAD_REQUEST, "Domene feil", adressebokId, null, dme);
        }
    }

    private ApplicationException createApplicationException(Status status, String message, AdressebokId adressebokId,
            KontaktId kontaktId, DomeneException dme) {
        Optional<net.innovatec.adressebok.domain.model.AdressebokId> optionalAdressebokId = adressebokId != null
                ? Optional.of(adressebokId)
                : Optional.empty();
        Optional<net.innovatec.adressebok.domain.model.KontaktId> optionalKontaktId = kontaktId != null
                ? Optional.of(kontaktId)
                : Optional.empty();
        if (status.equals(Status.NOT_FOUND))
            return new NotFoundException(message, optionalAdressebokId, optionalKontaktId, dme);
        else
            return new BadRequestException(message, optionalAdressebokId, optionalKontaktId, dme);

    }
}
