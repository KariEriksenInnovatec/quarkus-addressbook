package net.innovatec.adressebok.infrastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;
import net.innovatec.adressebok.domain.AdressebokRepo;
import net.innovatec.adressebok.domain.model.Adressebok;
import net.innovatec.adressebok.domain.model.AdressebokId;
import net.innovatec.adressebok.domain.model.Kontakt;
import net.innovatec.adressebok.domain.model.Navn;

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
    public Kontakt oppdaterKontakt(String adressebokId, String kontaktId, Kontakt oppdatertKontaktData) {

        // Hent eksisterende kontakt
        Kontakt eksisterendeKontakt = hentKontakt(adressebokId, kontaktId);

        // Oppdater navn hvis det er endret
        if (oppdatertKontaktData.hentNavn() != null) {
            eksisterendeKontakt.settNavn(oppdatertKontaktData.hentNavn());
        }

        // Slett alle eksisterende adresser
        List<net.innovatec.adressebok.domain.model.Adresse> eksisterendeAdresser = new ArrayList<>(eksisterendeKontakt.getAdresser());
        for (net.innovatec.adressebok.domain.model.Adresse adresse : eksisterendeAdresser) {
            eksisterendeKontakt.slettAdresse(adresse);
        }

        // Legg til nye adresser fra oppdatert data
        for (net.innovatec.adressebok.domain.model.Adresse nyAdresse : oppdatertKontaktData.getAdresser()) {
            eksisterendeKontakt.opprettAdresse(
                nyAdresse.adresseType(),
                nyAdresse.gatenavn(),
                nyAdresse.gatenummer(),
                nyAdresse.postnummer(),
                nyAdresse.by(),
                nyAdresse.land()
            );
        }

        // Slett alle eksisterende epost
        List<net.innovatec.adressebok.domain.model.Epost> eksisterendeEpost = new ArrayList<>(eksisterendeKontakt.getEpost());
        for (net.innovatec.adressebok.domain.model.Epost epost : eksisterendeEpost) {
            eksisterendeKontakt.slettEpost(epost);
        }

        // Legg til nye epost fra oppdatert data
        for (net.innovatec.adressebok.domain.model.Epost nyEpost : oppdatertKontaktData.getEpost()) {
            eksisterendeKontakt.opprettEpost(nyEpost.epostAdresse());
        }

        // Slett alle eksisterende telefoner
        List<net.innovatec.adressebok.domain.model.Telefon> eksisterendeTelefoner = new ArrayList<>(eksisterendeKontakt.getTelefon());
        for (net.innovatec.adressebok.domain.model.Telefon telefon : eksisterendeTelefoner) {
            eksisterendeKontakt.slettTelefon(telefon);
        }

        // Legg til nye telefoner fra oppdatert data
        for (net.innovatec.adressebok.domain.model.Telefon nyTelefon : oppdatertKontaktData.getTelefon()) {
            eksisterendeKontakt.opprettTelefon(nyTelefon.telefonType(), nyTelefon.telefonnummer());
        }

        return eksisterendeKontakt;
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
