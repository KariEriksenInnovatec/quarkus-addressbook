package net.innovatec.adressebok.domain;

import java.util.ArrayList;
import java.util.List;

public final class Adressebok {

    private AdressebokId id = null;
    private List<Kontakt> kontakter = new ArrayList<Kontakt>();

    public Adressebok(AdressebokId id, List<Kontakt> kontakter) {
        this.id = id;
        this.kontakter = kontakter;
    }

    public AdressebokId hentId() {
        return id;
    }

    public List<Kontakt> hentKontakter() {
        return kontakter;
    }

    public Kontakt opprettKontakt(KontaktId id, KontaktData data) {

        Kontakt nyKontakt = opprettKontakt(id, data);
        kontakter.add(nyKontakt);

        return nyKontakt;
    }

    public Kontakt hentKontakt(KontaktId id) {
        throw new RuntimeException("Not implemented yet");
    }

    public void oppdatereKontakt(Kontakt kontakt) {
        throw new RuntimeException("Not implemented yet");
    }

    public void slettKontakt(Kontakt kontakt) {
        throw new RuntimeException("Not implemented yet");
    }

    public Kontakt søkKontakt(String kriterie) {
        throw new RuntimeException("Not implemented yet");
    }

    public Kontakt søkKontakt(Navn navn) {
        throw new RuntimeException("Not implemented yet");
    }
}
