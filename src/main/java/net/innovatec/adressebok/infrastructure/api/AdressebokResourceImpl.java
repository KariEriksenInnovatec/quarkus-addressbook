package net.innovatec.adressebok.infrastructure.api;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.core.Response;
import net.innovatec.adressebok.application.AdressebokServiceImpl;
import net.innovatec.adressebok.infrastructure.api.beans.Adressebok;
import net.innovatec.adressebok.infrastructure.api.beans.AdressebokId;
import net.innovatec.adressebok.infrastructure.api.beans.Kontakt;
import net.innovatec.adressebok.infrastructure.api.beans.KontaktData;
import net.innovatec.adressebok.infrastructure.api.beans.KontaktId;
import net.innovatec.adressebok.infrastructure.mapper.AdressebokMapper;

@ApplicationScoped
public class AdressebokResourceImpl implements AdressebokResource {

    @Inject
    AdressebokServiceImpl service;

    @Inject
    AdressebokMapper mapper;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Adressebok hentAdressebok(@Valid @NotNull String adressebokId) {
        net.innovatec.adressebok.domain.model.AdressebokId id = mapper.stringToAdressebokId(adressebokId);
        return mapper.toDto(service.hentAdressebok(id));
    }

    @Override
    public AdressebokId opprettAdressebok() {
        return mapper.toDto(service.opprettAdressebok());
    }

    @Override
    public void slettAdressebok(@Valid @NotNull String adressebokId) {
        service.slettAdressebok(mapper.stringToAdressebokId(adressebokId));
    }

    @Override
    public AdressebokId importerAdressebok(@Valid @NotNull Adressebok bok) {
        net.innovatec.adressebok.domain.model.Adressebok domainAdressebok = mapper.toDomain(bok);
        net.innovatec.adressebok.domain.model.AdressebokId domainAdressebokId = service
                .lagreAdressebok(domainAdressebok);
        return mapper.toDto(domainAdressebokId);
    }

    @Override
    public Response eksporterAdressebok(@Valid @NotNull String adressebokId) {
        // try {
            net.innovatec.adressebok.domain.model.Adressebok domainAdressebok = service
                    .hentAdressebok(mapper.stringToAdressebokId(adressebokId));

            Adressebok dtoAdressebok = mapper.toDto(domainAdressebok);
            /*File file;
            file = File.createTempFile("adressebok_" + adressebokId, ".json");
            objectMapper.writeValue(file, dtoAdressebok);

            return Response.ok(dtoAdressebok).header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"")
                    .header("Content-Type", Files.probeContentType(file.toPath())) // Automatically detect content type
                    .build();
            */
            return Response.ok(dtoAdressebok)  // Direct JSON, no File
                .header("Content-Disposition", "attachment; filename=\"adressebok_" + adressebokId + ".json\"")
                .header("Content-Type", "application/json")
                .build();                    
        /* } catch (IOException e) {
            throw new RuntimeException("Failed to write adressebok to file", e);
        }*/
    }

    @Override
    public KontaktId opprettKontakt(@Valid @NotNull String adressebokId, @Valid @NotNull KontaktData dtoKontakt) {
        net.innovatec.adressebok.domain.model.Adressebok bok = service
                .hentAdressebok(mapper.stringToAdressebokId(adressebokId));
        net.innovatec.adressebok.domain.model.KontaktData dmoKontakt = mapper.toDomain(dtoKontakt);
        net.innovatec.adressebok.domain.model.KontaktId dmoKontaktId = service.opprettOgLeggTilKontakt(bok.hentId(),
                dmoKontakt);
        KontaktId dtoKontaktId = new KontaktId();
        dtoKontaktId.setValue(dmoKontaktId.getId());
        return dtoKontaktId;
    }

    @Override
    public Kontakt hentKontakt(@Valid @NotNull String adressebokId, @Valid @NotNull String kontaktId) {
        net.innovatec.adressebok.domain.model.Kontakt dmoKontakt = service
                .hentKontakt(mapper.stringToAdressebokId(adressebokId), mapper.stringToKontaktId(kontaktId));
        Kontakt dtoKontakt = mapper.toDto(dmoKontakt);
        return dtoKontakt;
    }

    @Override
    public void oppdaterKontakt(@Valid @NotNull String adressebokId, @Valid @NotNull String kontaktId,
            @Valid @NotNull KontaktData data) {
        net.innovatec.adressebok.domain.model.Adressebok bok = service
                .hentAdressebok(mapper.stringToAdressebokId(adressebokId));
        bok.oppdatereKontakt(mapper.stringToKontaktId(kontaktId), mapper.toDomain(data));
        service.lagreAdressebok(bok);
    }

    @Override
    public void slettKontakt(@Valid @NotNull String adressebokId, @Valid @NotNull String kontaktId) {
        service.slettKontakt(mapper.stringToAdressebokId(adressebokId), mapper.stringToKontaktId(kontaktId));
    }

    @Override
    public List<Kontakt> sokKontakt(@Valid @NotNull String adressebokId, @Valid String navn) {
        List<net.innovatec.adressebok.domain.model.Kontakt> resultat = service
                .søkKontakt(mapper.stringToAdressebokId(adressebokId), navn);
        return mapper.mapKontakterToList(resultat);
    }

}
