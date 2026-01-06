package net.innovatec.adressebok.infrastructure.persistence;

import static net.innovatec.adressebok.dao.Tables.ADRESSE;
import static net.innovatec.adressebok.dao.Tables.ADRESSEBOK_;
import static net.innovatec.adressebok.dao.Tables.EPOST;
import static net.innovatec.adressebok.dao.Tables.KONTAKT;
import static net.innovatec.adressebok.dao.Tables.TELEFON;
import static org.jooq.impl.DSL.multiset;
import static org.jooq.impl.DSL.select;

import java.security.NoSuchAlgorithmException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.jooq.DSLContext;
import org.jooq.Records;

import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import net.innovatec.adressebok.domain.AdressebokRepository;
import net.innovatec.adressebok.domain.model.Adressebok;
import net.innovatec.adressebok.domain.model.AdressebokId;
import net.innovatec.adressebok.domain.model.IkkeFunnetDomeneException;
import net.innovatec.adressebok.domain.model.KontaktId;
import net.innovatec.adressebok.domain.model.Navn;
import net.innovatec.adressebok.infrastructure.util.ChecksumUtil;

public class DatabaseAdressebokRepository implements AdressebokRepository {
    @Inject
    DSLContext ctx;
    
    public DatabaseAdressebokRepository(DSLContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public net.innovatec.adressebok.domain.model.Adressebok opprettAdressebok() {
        AdressebokId id = lagreAdressebok(new Adressebok());
        return hentAdressebok(id);
    }

    @Transactional
    @Override
    public AdressebokId lagreAdressebok(net.innovatec.adressebok.domain.model.Adressebok bok) {
        // Insert or update the adressebok record
        ctx.insertInto(ADRESSEBOK_)
           .set(ADRESSEBOK_.ADRESSEBOK_ID, bok.hentId())
           .onDuplicateKeyUpdate()
           .set(ADRESSEBOK_.OPPDATERT_TID, OffsetDateTime.now(ZoneOffset.UTC))
           .execute();

        // Delete contacts that are still in database, but not in domain model
        Set<KontaktId> fraDatabase = hentKontakter(bok.hentId()).stream().collect(Collectors.toSet());
        Set<KontaktId> fraDomene = bok.hentKontakter()
                                      .stream()
                                      .map(net.innovatec.adressebok.domain.model.Kontakt::hentId)
                                      .collect(Collectors.toSet());
        // Remove the ones that are still in the domain model
        fraDatabase.removeAll(fraDomene);

        if (fraDatabase.size() > 0) {
            ctx.deleteFrom(KONTAKT).where(KONTAKT.KONTAKT_ID.in(fraDatabase)).execute();
        }

        // Iterate over each kontakt in the adressebok
        for (var kontakt : bok.hentKontakter()) {
            try {
                String checksum = ChecksumUtil.calculateChecksum(kontakt);

                String currentChecksum = ctx.select(KONTAKT.CHECKSUM)
                        .from(KONTAKT)
                        .where(KONTAKT.KONTAKT_ID.eq(kontakt.hentId()))
                        .fetchOneInto(String.class);
                
                if(!checksum.equals(currentChecksum)) {
                // Insert or update the kontakt record
                ctx.insertInto(KONTAKT)
                   .set(KONTAKT.KONTAKT_ID, kontakt.hentId())
                   .set(KONTAKT.ADRESSEBOK_ID, bok.hentId())
                   .set(KONTAKT.FORNAVN, kontakt.hentData().navn().fornavn())
                   .set(KONTAKT.ETTERNAVN, kontakt.hentData().navn().etternavn())
                   .set(KONTAKT.CHECKSUM, checksum)
                   .onDuplicateKeyUpdate()
                   .set(KONTAKT.FORNAVN, kontakt.hentData().navn().fornavn())
                   .set(KONTAKT.ETTERNAVN, kontakt.hentData().navn().etternavn())
                   .set(KONTAKT.CHECKSUM, checksum)
                   .set(KONTAKT.OPPDATERT_TID, OffsetDateTime.now(ZoneOffset.UTC))
                   // .where(KONTAKT.CHECKSUM.ne(checksum))
                   .execute();
                } else {
                    continue;
                }
                
                // Delete existing addresses
                ctx.deleteFrom(ADRESSE).where(ADRESSE.KONTAKT_ID.eq(kontakt.hentId())).execute();

                // Insert or update the adresse records
                for (var adresse : kontakt.hentData().adresser()) {

                    ctx.insertInto(ADRESSE)
                       .set(ADRESSE.KONTAKT_ID, kontakt.hentId())
                       .set(ADRESSE.ADRESSE_TYPE, adresse.type())
                       .set(ADRESSE.GATENAVN, adresse.gatenavn())
                       .set(ADRESSE.GATENUMMER, adresse.gatenummer())
                       .set(ADRESSE.POSTNUMMER, adresse.postnummer())
                       .set(ADRESSE.STED, adresse.by())
                       .set(ADRESSE.LAND, adresse.landkode())
                       .execute();
                }

                // Delete existing epost
                ctx.deleteFrom(EPOST).where(EPOST.KONTAKT_ID.eq(kontakt.hentId())).execute();

                // Insert or update the epost records
                for (var epost : kontakt.hentData().epostadresser()) {

                    ctx.insertInto(EPOST)
                       .set(EPOST.KONTAKT_ID, kontakt.hentId())
                       .set(EPOST.EPOSTADRESSE, epost.epostadresse())
                       .execute();
                }

                // Delete existing telefon
                ctx.deleteFrom(TELEFON).where(TELEFON.KONTAKT_ID.eq(kontakt.hentId())).execute();

                // Insert or update the telefon records
                for (var telefon : kontakt.hentData().telefonnumre()) {

                    ctx.insertInto(TELEFON)
                       .set(TELEFON.KONTAKT_ID, kontakt.hentId())
                       .set(TELEFON.TELEFON_TYPE, telefon.type())
                       .set(TELEFON.LANDSKODE, telefon.landskode())
                       .set(TELEFON.TELEFONNUMMER, telefon.nummer())
                       .execute();
                }

            } catch (NoSuchAlgorithmException e) {
                Log.error("Problem calculating checksum!", e);

            }
        }

        return bok.hentId();
    }

    private List<KontaktId> hentKontakter(AdressebokId id) {
        return ctx.select(KONTAKT.KONTAKT_ID)
                  .from(KONTAKT)
                  .where(KONTAKT.ADRESSEBOK_ID.eq(id))
                  .fetchInto(KontaktId.class);
    }

    @Override
    public net.innovatec.adressebok.domain.model.Adressebok hentAdressebok(String uuid) {
        return hentAdressebok(new AdressebokId(uuid));
    }

    @Override
    public net.innovatec.adressebok.domain.model.Adressebok hentAdressebok(AdressebokId adressebokId) {
        // Does id exist in database?
        List<net.innovatec.adressebok.domain.model.AdressebokId> ids = ctx.select(ADRESSEBOK_.ADRESSEBOK_ID)
                                                                          .from(ADRESSEBOK_)
                                                                          .where(ADRESSEBOK_.ADRESSEBOK_ID.eq(
                                                                                  adressebokId))
                                                                          .fetchInto(AdressebokId.class);

        if (ids.isEmpty())
            throw new IkkeFunnetDomeneException("Adressebok eksisterer ikke!");

        net.innovatec.adressebok.domain.model.Adressebok adressebok = new net.innovatec.adressebok.domain.model.Adressebok(
                adressebokId);

        var result = ctx.select(KONTAKT.KONTAKT_ID, KONTAKT.FORNAVN, KONTAKT.ETTERNAVN, multiset(select(
                ADRESSE.ADRESSE_TYPE, ADRESSE.GATENAVN, ADRESSE.GATENUMMER, ADRESSE.POSTNUMMER, ADRESSE.STED,
                ADRESSE.LAND).from(ADRESSE)
                             .where(ADRESSE.KONTAKT_ID.eq(KONTAKT.KONTAKT_ID)))
                                                                               .as("adresser")
                                                                               .convertFrom(r -> r.map(Records.mapping(
                                                                                       net.innovatec.adressebok.domain.model.Adresse::new))),
                multiset(select(EPOST.EPOSTADRESSE).from(
                        EPOST).where(EPOST.KONTAKT_ID.eq(KONTAKT.KONTAKT_ID)))
                                                                              .as("epostadresser")
                                                                              .convertFrom(r -> r.map(Records.mapping(
                                                                                      net.innovatec.adressebok.domain.model.Epost::new))),
                multiset(select(TELEFON.TELEFON_TYPE, TELEFON.LANDSKODE, TELEFON.TELEFONNUMMER).from(
                        TELEFON).where(TELEFON.KONTAKT_ID.eq(KONTAKT.KONTAKT_ID))).as("telefoner")
                                                                                  .convertFrom(
                                                                                          r -> r.map(Records.mapping(
                                                                                                  net.innovatec.adressebok.domain.model.Telefon::new))))
                        .from(KONTAKT)
                        .where(KONTAKT.ADRESSEBOK_ID.eq(adressebokId))
                        .fetch();

        result.forEach(row -> {
            KontaktId id = row.get(KONTAKT.KONTAKT_ID);
            Navn navn = new Navn(row.get(KONTAKT.FORNAVN), row.get(KONTAKT.ETTERNAVN));

            @SuppressWarnings("unchecked")
            Set<net.innovatec.adressebok.domain.model.Adresse> adresser = ((List<net.innovatec.adressebok.domain.model.Adresse>) row.get(
                    "adresser", List.class)).stream().collect(Collectors.toUnmodifiableSet());

            @SuppressWarnings("unchecked")
            Set<net.innovatec.adressebok.domain.model.Epost> epostadresser = ((List<net.innovatec.adressebok.domain.model.Epost>) row.get(
                    "epostadresser", List.class)).stream().collect(Collectors.toUnmodifiableSet());

            @SuppressWarnings("unchecked")
            Set<net.innovatec.adressebok.domain.model.Telefon> telefoner = ((List<net.innovatec.adressebok.domain.model.Telefon>) row.get(
                    "telefoner", List.class)).stream().collect(Collectors.toUnmodifiableSet());

            net.innovatec.adressebok.domain.model.KontaktData data = new net.innovatec.adressebok.domain.model.KontaktData(
                    navn, adresser, epostadresser, telefoner);
            net.innovatec.adressebok.domain.model.Kontakt kontakt = new net.innovatec.adressebok.domain.model.Kontakt(
                    id, data);
            adressebok.leggTilKontakt(kontakt);
        });

        return adressebok;
    }

    @Override
    public Boolean slettAdressebok(String uuid) {
        return slettAdressebok(new AdressebokId(uuid));
    }

    @Transactional
    @Override
    public Boolean slettAdressebok(AdressebokId adressebokId) {
        return ctx.deleteFrom(ADRESSEBOK_).where(ADRESSEBOK_.ADRESSEBOK_ID.eq(adressebokId)).execute() > 0 ? true
                : false;
    }

}
