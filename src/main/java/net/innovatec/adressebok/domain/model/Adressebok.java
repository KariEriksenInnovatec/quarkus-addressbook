package net.innovatec.adressebok.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Adressebok {

    // AdressebokId blir håntert av repo i infrastrukturlaget
    // Kommer til å hete AdressebokRepo

    private AdressebokId id = null;
    private List<Kontakt> kontakter = new ArrayList<>();

    public Adressebok(AdressebokId id) {
        this.id = id;
    }

    public AdressebokId hentId() {
        return id;
    }

    public List<Kontakt> hentKontakter() {
        return kontakter;
    }

    public Kontakt opprettKontakt(Navn navn) {

        // Genererer kontaktId direkte i metoden
        // For å sikre unik id for kontakt (skal kun gjøres ett sted, en gang)
        KontaktId id = new KontaktId(UUID.randomUUID());

        return new Kontakt(id, navn);
    }

    public Kontakt leggTilKontakt(Kontakt kontakt) {

        KontaktId id = kontakt.hentId();
        Navn navn = kontakt.hentNavn();

        if (id == null || navn == null) {
            throw new IllegalArgumentException(
                    "KontaktId og navn er påkrevd for å legge til en eksisterende kontakt.");
        }

        // Sjekk om det allerede finnes en kontakt med samme navn
        for (Kontakt eksisterendeKontakt : kontakter) {
            Navn eksisterendeNavn = eksisterendeKontakt.hentNavn();
            if (eksisterendeNavn.equals(navn)) {
                throw new IllegalArgumentException(
                        "En kontakt med navnet " + navn.fornavn() + " " + navn.etternavn() + " finnes allerede.");
            }
        }
        kontakter.add(kontakt);

        return kontakt;
    }

    public Kontakt hentKontakt(KontaktId id) {

        if (id == null) {
            throw new IllegalArgumentException("KontaktId kan ikke være null.");
        }

        for (Kontakt kontakt : kontakter) {
            if (kontakt.hentId().equals(id)) {
                return kontakt;
            }
        }
        throw new RuntimeException("Ingen kontakt med id " + id + " ble funnet.");
    }

    public void oppdatereKontakt(KontaktId id, Navn navn) {

        // KontaktId id = kontakt.hentId();
        // Navn navn = kontakt.hentNavn();
        Kontakt kontakt = hentKontakt(id);

        if (id == null) {
            throw new IllegalArgumentException("KontaktId kan ikke være null.");
        }
        if (navn == null) {
            throw new IllegalArgumentException("Nytt navn kan ikke være null.");
        }

        // Sjekk om det nye navnet allerede er i bruk av en annen kontakt
        for (Kontakt eksisterendeKontakt : kontakter) {
            if (!eksisterendeKontakt.equals(kontakt) && eksisterendeKontakt.hentNavn().equals(navn)) {
                throw new IllegalArgumentException(
                        "En annen kontakt har allerede navnet " + navn.fornavn() + " " + navn.etternavn());
            }
        }
        kontakt.settNavn(navn);
    }

    public void slettKontakt(Kontakt kontakt) {

        // Sjekker om kontakten som skal slettes finnes i adresseboken, og sletter
        // dersom den finnes
        if (!kontakter.remove(kontakt))
            throw new RuntimeException("Kan ikke slette kontakt, den finnes ikke i kontaktlisten.");
    }

    public List<Kontakt> søkKontakt(String kriterie) {
        if (kriterie == null || kriterie.isBlank()) {
            throw new IllegalArgumentException("Søkekriterie kan ikke være tomt.");
        }

        String kriterieLowerCase = kriterie.toLowerCase();
        List<Kontakt> resultater = new ArrayList<>();

        for (Kontakt kontakt : kontakter) {
            Navn navn = kontakt.hentNavn();
            String fornavn = navn.fornavn().toLowerCase();
            String etternavn = navn.etternavn().toLowerCase();

            // if (fornavn.contains(kriterieLowerCase) || etternavn.contains(kriterieLowerCase)) {
            if (fornavn.startsWith(kriterieLowerCase) || etternavn.startsWith(kriterieLowerCase)) {
                resultater.add(kontakt);
            }
        }
        return resultater;
    }

    public Kontakt søkKontakt(Navn navn) {
        if (navn == null) {
            throw new IllegalArgumentException("Navn kan ikke være null.");
        }

        for (Kontakt kontakt : kontakter) {
            if (kontakt.hentNavn().equals(navn)) {
                return kontakt;
            }
        }
        throw new RuntimeException(
                "Ingen kontakt med navnet " + navn.fornavn() + " " + navn.etternavn() + " ble funnet.");
    }
}
