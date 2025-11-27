package net.innovatec.adressebok.infrastructure.api;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import org.jboss.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import net.innovatec.adressebok.application.AdressebokServiceImpl;
import net.innovatec.adressebok.domain.model.DomeneException;
import net.innovatec.adressebok.domain.model.IkkeFunnetDomeneException;
import net.innovatec.adressebok.infrastructure.api.beans.Adressebok;
import net.innovatec.adressebok.infrastructure.api.beans.AdressebokId;
import net.innovatec.adressebok.infrastructure.api.beans.Kontakt;
import net.innovatec.adressebok.infrastructure.api.beans.KontaktData;
import net.innovatec.adressebok.infrastructure.api.beans.KontaktId;
import net.innovatec.adressebok.infrastructure.exception.ApplicationException;
import net.innovatec.adressebok.infrastructure.exception.BadRequestException;
import net.innovatec.adressebok.infrastructure.exception.NotFoundException;
import net.innovatec.adressebok.infrastructure.mapper.AdressebokMapper;

public class AdressebokResourceImpl implements AdressebokResource {
    private final Logger log = Logger.getLogger(this.getClass());

    @Inject
    AdressebokServiceImpl service;

    @Inject
    AdressebokMapper mapper;
    
    private final ObjectMapper objectMapper = new ObjectMapper();    

    @Override
    public Adressebok hentAdressebok(@Valid @NotNull String adressebokId) {
        try {
            net.innovatec.adressebok.domain.model.AdressebokId id = mapper.stringToAdressebokId(adressebokId);
            return mapper.toDto(service.hentAdressebok(id));
        } catch (IkkeFunnetDomeneException nfe) {
            throw createApplicationException(Status.NOT_FOUND, "Domene feil", adressebokId, null, nfe);
        } catch (DomeneException dme) {
            throw createApplicationException(Status.BAD_REQUEST, "Domene feil", adressebokId, null, dme);
        }
    }

    @Override
    public AdressebokId opprettAdressebok() {
        return mapper.toDto(service.opprettAdressebok());
    }

    @Override
    public void slettAdressebok(@Valid @NotNull String adressebokId) {
        try {
            service.slettAdressebok(mapper.stringToAdressebokId(adressebokId));
        } catch (IkkeFunnetDomeneException nfe) {
            throw createApplicationException(Status.NOT_FOUND, "Domene feil", adressebokId, null, nfe);
        }
    }

    @Override
    public AdressebokId importerAdressebok(@Valid @NotNull Adressebok bok) {
        try {
            net.innovatec.adressebok.domain.model.Adressebok domainAdressebok = mapper.toDomain(bok);
            net.innovatec.adressebok.domain.model.AdressebokId domainAdressebokId = service
                    .importerAdressebok(domainAdressebok);
            return mapper.toDto(domainAdressebokId);
        } catch (DomeneException dme) {
            throw createApplicationException(Status.BAD_REQUEST, "Feil ved importering av adressebok", null, null, dme);
        }
    }
    
    @Override
    public Response eksporterAdressebok(@Valid @NotNull String adressebokId) {
        try {
            net.innovatec.adressebok.domain.model.Adressebok domainAdressebok = service.hentAdressebok(mapper.stringToAdressebokId(adressebokId));

            Adressebok dtoAdressebok = mapper.toDto(domainAdressebok);
            File file = File.createTempFile("adressebok_" + adressebokId, ".json");
            objectMapper.writeValue(file, dtoAdressebok);

            return Response.ok(file)
                    .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"")
                    .header("Content-Type", Files.probeContentType(file.toPath())) // Automatically detect content type
                    .build();
        } catch (IOException e) {
            throw new RuntimeException("Failed to write adressebok to file", e);
        } catch (IkkeFunnetDomeneException nfe) {
            throw createApplicationException(Status.NOT_FOUND, "Adressebok ikke funnet", adressebokId, null, nfe);
        } catch (DomeneException dme) {
            throw createApplicationException(Status.BAD_REQUEST, "Feil ved eksportering av adressebok", adressebokId, null, dme);
        }
    }
    
    @Override
    public KontaktId opprettKontakt(@Valid @NotNull String adressebokId, @Valid @NotNull KontaktData dtoKontakt) {
        try {
            net.innovatec.adressebok.domain.model.Adressebok bok = service
                    .hentAdressebok(mapper.stringToAdressebokId(adressebokId));
            net.innovatec.adressebok.domain.model.KontaktData dmoKontakt = mapper.toDomain(dtoKontakt);
            net.innovatec.adressebok.domain.model.KontaktId dmoKontaktId = service.opprettOgLeggTilKontakt(bok.hentId(),
                    dmoKontakt);
            KontaktId dtoKontaktId = new KontaktId();
            dtoKontaktId.setValue(dmoKontaktId.getId());
            return dtoKontaktId;
        } catch (IkkeFunnetDomeneException nfe) {
            throw createApplicationException(Status.NOT_FOUND, "Domene feil", adressebokId, null, nfe);
        } catch (DomeneException dme) {
            throw createApplicationException(Status.BAD_REQUEST, "Domene feil", adressebokId, null, dme);
        }

    }

    @Override
    public Kontakt hentKontakt(@Valid @NotNull String adressebokId, @Valid @NotNull String kontaktId) {
        try {
            net.innovatec.adressebok.domain.model.Kontakt dmoKontakt = service
                    .hentKontakt(mapper.stringToAdressebokId(adressebokId), mapper.stringToKontaktId(kontaktId));
            Kontakt dtoKontakt = mapper.toDto(dmoKontakt);
            return dtoKontakt;
        } catch (IkkeFunnetDomeneException nfe) {
            throw createApplicationException(Status.NOT_FOUND, "Domene feil", adressebokId, kontaktId, nfe);
        } catch (DomeneException dme) {
            throw createApplicationException(Status.BAD_REQUEST, "Domene feil", adressebokId, kontaktId, dme);
        }
    }

    @Override
    public void oppdaterKontakt(@Valid @NotNull String adressebokId, @Valid @NotNull String kontaktId, @Valid @NotNull KontaktData data) {
        try {
            net.innovatec.adressebok.domain.model.Adressebok bok = service
                    .hentAdressebok(mapper.stringToAdressebokId(adressebokId));
            bok.oppdatereKontakt(mapper.stringToKontaktId(kontaktId), mapper.toDomain(data));
        } catch (IkkeFunnetDomeneException nfe) {
            throw createApplicationException(Status.NOT_FOUND, "Domene feil", adressebokId, kontaktId, nfe);
        } catch (DomeneException dme) {
            throw createApplicationException(Status.BAD_REQUEST, "Domene feil", adressebokId, kontaktId, dme);
        }
    }

    @Override
    public void slettKontakt(@Valid @NotNull String adressebokId, @Valid @NotNull String kontaktId) {
        try {
            service.slettKontakt(mapper.stringToAdressebokId(adressebokId), mapper.stringToKontaktId(kontaktId));
        } catch (DomeneException dme) {
            throw createApplicationException(Status.NOT_FOUND, "Domene feil", adressebokId, kontaktId, dme);
        }
    }

    @Override
    public List<Kontakt> sokKontakt(@Valid @NotNull String adressebokId, @Valid String navn) {
        try {
            List<net.innovatec.adressebok.domain.model.Kontakt> resultat = service
                    .søkKontakt(mapper.stringToAdressebokId(adressebokId), navn);
            return mapper.mapKontakterToList(resultat);
        } catch (DomeneException dme) {
            throw createApplicationException(Status.BAD_REQUEST, "Domene feil", adressebokId, null, dme);
        }
    }

    private ApplicationException createApplicationException(Status status, String message, String adressebokId,
            String kontaktId, DomeneException dme) {
        Optional<net.innovatec.adressebok.domain.model.AdressebokId> optionalAdressebokId = adressebokId != null
                ? Optional.of(new net.innovatec.adressebok.domain.model.AdressebokId(adressebokId))
                : Optional.empty();
        Optional<net.innovatec.adressebok.domain.model.KontaktId> optionalKontaktId = kontaktId != null
                ? Optional.of(new net.innovatec.adressebok.domain.model.KontaktId(kontaktId))
                : Optional.empty();
        if (status.equals(Status.NOT_FOUND))
            return new NotFoundException(message, optionalAdressebokId, optionalKontaktId, dme);
        else
            return new BadRequestException(message, optionalAdressebokId, optionalKontaktId, dme);

    }
}
