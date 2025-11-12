package net.innovatec.adressebok.domain;

public class Kontakt {

    private KontaktId id = null;
    private KontaktData data = null;

    public Kontakt(KontaktId id, KontaktData data) {
        this.id = id;
        this.data = data;
    }

    public KontaktId hentId() {
        return id;
    }

    public KontaktData hentKontaktData() {
        return data;
    }

    public Adresse opprettAdresse(AdresseType adresseType, String gatenavn, String gatenummer, String postnummer,
            String by,
            String land) {

        // Sjekker om det opprettes flere enn 2 adresser på en kontakt
        // Se domenemodellen
        if (data.adresser().size() == 2)
            throw new RuntimeException("Ikke lov å opprette mer enn 2 adresser.");
        if (!data.adresser().isEmpty() && data.adresser().getFirst().adresseType() == adresseType)
            throw new RuntimeException("Ikke lov med to adresser av samme type.");

        Adresse nyAdresse = opprettAdresse(adresseType, gatenavn, gatenummer, postnummer, by, land);
        data.adresser().add(nyAdresse);

        return nyAdresse;
    }

    public void slettAdresse(Adresse adresse) {

        // Sjekker om adressen som skal slettes finnes på kontakten, og sletter dersom
        // den finnes
        if (!data.adresser().remove(adresse))
            throw new RuntimeException("Kan ikke slette adressen, den finnes ikke i adresselisten.");

    }

    public Epost opprettEpost(String epostAdresse) {

        // Sjekker om det finnes 3 eller flere e-post adresser allerede
        if (data.epost().size() >= 3)
            throw new RuntimeException("Ikke lov å opprette mer enn 3 e-post adresser.");

        Epost nyEpost = opprettEpost(epostAdresse);
        data.epost().add(nyEpost);

        return nyEpost;
    }

    public void slettEpost(Epost epost) {

        if (!data.epost().remove(epost))
            throw new RuntimeException("Kan ikke slette e-post, den finnes ikke i listen.");
    }

    public Telefon opprettTelefon(TelefonType telefonType, String telefonnummer) {

        if (data.telefon().size() == 10)
            throw new RuntimeException("Ikke lov å opprette mer enn 10 telefonnummer.");

        Telefon nyTelefon = opprettTelefon(telefonType, telefonnummer);

        return nyTelefon;
    }

    public void slettTelefon(Telefon telefon) {

        if (!data.telefon().remove(telefon))
            throw new RuntimeException("Kan ikke slette telefonnummer, den finnes ikke i listen.");

    }
}
