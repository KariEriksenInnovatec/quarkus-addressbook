package net.innovatec.adressebok.infrastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;
import net.innovatec.adressebok.domain.AdressebokRepo;
import net.innovatec.adressebok.domain.model.AdresseType;
import net.innovatec.adressebok.domain.model.Adressebok;
import net.innovatec.adressebok.domain.model.AdressebokId;
import net.innovatec.adressebok.domain.model.Kontakt;
import net.innovatec.adressebok.domain.model.Navn;
import net.innovatec.adressebok.domain.model.TelefonType;

@ApplicationScoped
public class AdressebokRepoImpl implements AdressebokRepo {

    private List<Adressebok> adresseboker = new ArrayList<>();

    @Override
    public List<Adressebok> hentAlleAdresseboker() {
        return new ArrayList<>(adresseboker);
    }

    @Override
    public Adressebok opprettAdressebok() {

        // Generer en ny ID
        // Repo eier ID; unngår dupliketer, skal lagrets i database senere
        AdressebokId id = new AdressebokId(UUID.randomUUID());

        // Opprett og lagre en ny adressebok
        Adressebok adressebok = new Adressebok(id);
        adresseboker.add(adressebok);

        return adressebok;
    }

    @Override
    public Adressebok hentAdressebok(String adressebokId) {

        // Parse string-ID til UUID
        UUID uuid = UUID.fromString(adressebokId);

        // Søk gjennom alle adressebøker
        // Matche mot UUID, ikke String: raskere, tryggere, validering
        for (Adressebok adressebok : adresseboker) {
            if (adressebok.hentId().adressebokId().equals(uuid)) {
                return adressebok;
            }
        }
        // Hvis ikke funnet, kast exception
        throw new RuntimeException("Adressebok med id " + adressebokId + " ble ikke funnet");
    }

    @Override
    public void slettAdressebok(String adressebokId) {
        // Parse string-ID til UUID
        UUID uuid = UUID.fromString(adressebokId);

        // Søk gjennom alle adressebøker og fjern den med matching UUID
        for (int i = 0; i < adresseboker.size();) {
            if (adresseboker.get(i).hentId().adressebokId().equals(uuid)) {
                adresseboker.remove(i);

            }
            // Hvis ikke funnet, kast exception
            throw new RuntimeException("Adressebok med id " + adressebokId + " ble ikke funnet");
        }
    }

    @Override
    public List<Kontakt> hentKontakter(String adressebokId) {

        Adressebok adressebok = hentAdressebok(adressebokId);

        return adressebok.hentKontakter();
    }

    @Override
    public Kontakt opprettKontakt(String adressebokId, String fornavn, String etternavn) {

        Adressebok adressebok = hentAdressebok(adressebokId);
        Navn navn = new Navn(fornavn, etternavn);
        Kontakt nyKontakt = adressebok.opprettKontakt(navn);

        return adressebok.leggTilKontakt(nyKontakt);
    }

    @Override
    public Kontakt hentKontakt(String adressebokId, String kontaktId) {

        Adressebok adressebok = hentAdressebok(adressebokId);

        UUID uuid = UUID.fromString(kontaktId);

        for (Kontakt kontakt : adressebok.hentKontakter()) {
            if (kontakt.hentId().kontaktId().equals(uuid)) {
                return kontakt;
            }
        }
        throw new RuntimeException("Kontakt med id " + kontaktId + " ble ikke funnet");
    }

    @Override
    public Kontakt oppdaterKontakt(String adressebokId, String kontaktId, AdresseType adresseType, String gatenavn,
            String gatenummer, String postnummer,
            String by,
            String land, String epost, TelefonType telefonType, String telefonnummer) {

        Kontakt kontakt = hentKontakt(adressebokId, kontaktId);
        kontakt.opprettAdresse(adresseType, gatenavn, gatenummer, postnummer, by, land);
        kontakt.opprettEpost(epost);
        kontakt.opprettTelefon(telefonType, telefonnummer);

        return kontakt;
    }

    @Override
    public void slettKontakt(String adressebokId, String kontaktId) {

        UUID uuid = UUID.fromString(kontaktId);
        List<Kontakt> kontakter = hentKontakter(adressebokId);

        for (int i = 0; i < kontakter.size();) {
            if (kontakter.get(i).hentId().kontaktId().equals(uuid)) {
                kontakter.remove(i);

            }
            throw new RuntimeException("Kontakt med id " + kontaktId + " ble ikke funnet");
        }
    }
}
