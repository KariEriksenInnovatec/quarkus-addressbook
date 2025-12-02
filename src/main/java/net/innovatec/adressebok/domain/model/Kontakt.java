package net.innovatec.adressebok.domain.model;

import java.util.ArrayList;
import java.util.List;

public class Kontakt {

    private KontaktId id = null;
    private Navn navn = null;
    private List<Adresse> adresser = new ArrayList<>();
    private List<Epost> epost = new ArrayList<>();
    private List<Telefon> telefon = new ArrayList<>();

    public Kontakt(KontaktId id, Navn navn) {
        this.id = id;
        this.navn = navn;
    }

    public KontaktId hentId() {
        return id;
    }

    public Navn hentNavn() {
        return navn;
    }

    public void settNavn(Navn nyttNavn) {
        
        if (nyttNavn == null) {
            throw new IllegalArgumentException("Navn kan ikke være null.");
        }
        this.navn = nyttNavn;
    }

    public Navn getNavn() {
        return hentNavn();
    }

    public List<Adresse> getAdresser() {
        return adresser;
    }

    public List<Epost> getEpost() {
        return epost;
    }

    public List<Telefon> getTelefon() {
        return telefon;
    }
    
    public Adresse opprettAdresse(AdresseType adresseType, String gatenavn, String gatenummer, String postnummer,
            String by,
            String land) {

        // Sjekker om det opprettes flere enn 2 adresser på en kontakt
        // Se domenemodellen
        if (adresser.size() >= 2)
            throw new RuntimeException("Ikke lov å opprette mer enn 2 adresser.");
        if (!adresser.isEmpty() && adresser.getFirst().adresseType() == adresseType)
            throw new RuntimeException("Ikke lov med to adresser av samme type.");

        Adresse nyAdresse = new Adresse(adresseType, gatenavn, gatenummer, postnummer, by, land);
        adresser.add(nyAdresse);

        return nyAdresse;
    }

    public void slettAdresse(Adresse adresseSomSkalSlettes) {

        // Sjekker om adressen som skal slettes finnes på kontakten, og sletter dersom
        // den finnes
        if (!adresser.remove(adresseSomSkalSlettes))
            throw new RuntimeException("Kan ikke slette adressen, den finnes ikke i adresselisten.");
    }

    public Epost opprettEpost(String epostAdresse) {

        // Sjekker om det finnes 3 eller flere e-post adresser allerede
        if (epost.size() >= 3)
            throw new RuntimeException("Ikke lov å opprette mer enn 3 e-post adresser.");

        Epost nyEpost = new Epost(epostAdresse);
        epost.add(nyEpost);

        return nyEpost;
    }

    public void slettEpost(Epost epostSomSkalSlettes) {

        if (!epost.remove(epostSomSkalSlettes))
            throw new RuntimeException("Kan ikke slette e-post, den finnes ikke i listen.");
    }

    public Telefon opprettTelefon(TelefonType telefonType, String telefonnummer) {

        if (telefon.size() >= 10)
            throw new RuntimeException("Ikke lov å opprette mer enn 10 telefonnummer.");

        Telefon nyTelefon = new Telefon(telefonType, telefonnummer);
        telefon.add(nyTelefon);

        return nyTelefon;
    }

    public void slettTelefon(Telefon telefonSomSkalSlettes) {

        if (!telefon.remove(telefonSomSkalSlettes))
            throw new RuntimeException("Kan ikke slette telefonnummer, den finnes ikke i listen.");

    }
}
