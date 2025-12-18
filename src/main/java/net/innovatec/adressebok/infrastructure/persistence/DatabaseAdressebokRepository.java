package net.innovatec.adressebok.infrastructure.persistence;

import static net.innovatec.adressebok.dao.Tables.*;
import static org.jooq.impl.DSL.*;

import java.util.Set;
import java.util.stream.Collectors;

import org.jooq.DSLContext;
import org.jooq.Result;

import jakarta.inject.Inject;
import net.innovatec.adressebok.dao.tables.records.AdresseRecord;
import net.innovatec.adressebok.dao.tables.records.EpostRecord;
import net.innovatec.adressebok.dao.tables.records.TelefonRecord;
import net.innovatec.adressebok.domain.AdressebokRepository;
import net.innovatec.adressebok.domain.model.AdressebokId;
import net.innovatec.adressebok.domain.model.KontaktId;
import net.innovatec.adressebok.domain.model.Navn;

// @ApplicationScoped
public class DatabaseAdressebokRepository implements AdressebokRepository {
    @Inject
    DSLContext ctx;
    
    public DatabaseAdressebokRepository() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public net.innovatec.adressebok.domain.model.Adressebok opprettAdressBok() {
        return null;
    }

    @Override
    public AdressebokId leggTilAdressebok(net.innovatec.adressebok.domain.model.Adressebok bok) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public net.innovatec.adressebok.domain.model.Adressebok hentAdressebok(String uuid) {
//        Adressebok result = ctx.select(ADRESSEBOK_.ADRESSEBOK_ID)
//        .from(ADRESSEBOK_)
//        .where(ADRESSEBOK_.ADRESSEBOK_ID.eq(UUID.fromString(uuid)))
//        .fetchOne(Records.mapping(r -> new Adressebok(r.get(0, AdressebokId.class))));
        return null;
    }

    @Override
    public net.innovatec.adressebok.domain.model.Adressebok hentAdressebok(AdressebokId adressebokId) {
        var result = ctx.select(
                KONTAKT.KONTAKT_ID, KONTAKT.FORNAVN, KONTAKT.ETTERNAVN,
                multiset(select(ADRESSE.asterisk())
                    .from(ADRESSE)
                    .where(ADRESSE.KONTAKT_ID.eq(KONTAKT.KONTAKT_ID)))
                    .as("adresser"),
                multiset(select(EPOST.asterisk())
                    .from(EPOST)
                    .where(EPOST.KONTAKT_ID.eq(KONTAKT.KONTAKT_ID)))
                    .as("epostadresser"),
                multiset(select(TELEFON.asterisk())
                    .from(TELEFON)
                    .where(TELEFON.KONTAKT_ID.eq(KONTAKT.KONTAKT_ID)))
                    .as("telefoner")
            )
            .from(KONTAKT)
            .where(KONTAKT.ADRESSEBOK_ID.eq(adressebokId.getId()))
            .fetch();

        result.forEach(row -> {
            KontaktId id = new KontaktId(row.get(KONTAKT.KONTAKT_ID));
            Navn navn = new Navn(row.get(KONTAKT.FORNAVN), row.get(KONTAKT.ETTERNAVN));
            
            @SuppressWarnings("unchecked")
            Result<AdresseRecord> adresseResult =
                    row.get("adresser", Result.class);  // from MULTISET

            
            Set<net.innovatec.adressebok.domain.model.Adresse> adresser = adresseResult
                    .stream()
                    .map(r -> new net.innovatec.adressebok.domain.model.Adresse(
                            r.get(ADRESSE.ADRESSE_TYPE),
                            r.get(ADRESSE.GATENAVN),
                            r.get(ADRESSE.GATENUMMER),
                            r.get(ADRESSE.POSTNUMMER),
                            r.get(ADRESSE.STED),
                            r.get(ADRESSE.LAND)
                    ))
                    .collect(Collectors.toUnmodifiableSet());
            
            @SuppressWarnings("unchecked")
            Result<EpostRecord> epostResult =
                    row.get("epostadresser", Result.class);  // from MULTISET

            Set<net.innovatec.adressebok.domain.model.Epost> epostadresser = epostResult
                    .stream()
                    .map(r -> new net.innovatec.adressebok.domain.model.Epost(
                            r.get(EPOST.EPOSTADRESSE)
                    ))                    
                    .collect(Collectors.toUnmodifiableSet());

            @SuppressWarnings("unchecked")
            Result<TelefonRecord> telefonResult =
                    row.get("telefoner", Result.class);  // from MULTISET

            Set<net.innovatec.adressebok.domain.model.Telefon> telefonadresser = telefonResult
                    .stream()
                    .map(r -> new net.innovatec.adressebok.domain.model.Telefon(
                            r.get(TELEFON.TELEFON_TYPE),
                            r.get(TELEFON.LANDSKODE),
                            r.get(TELEFON.TELEFONNUMMER)
                    ))                    
                    .collect(Collectors.toUnmodifiableSet());
            
            // KontaktData data = new KontaktData(navn, adresser, eposter, telefoner);
            // Add to adressebok...
        });
        
        return null;
    }
    
    @Override
    public Boolean slettAdressebok(String uuid) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean slettAdressebok(AdressebokId adressebokId) {
        // TODO Auto-generated method stub
        return null;
    }

}
