package net.innovatec.adressebok.domain;

import java.util.List;

import net.innovatec.adressebok.domain.model.AdresseType;
import net.innovatec.adressebok.domain.model.Adressebok;
import net.innovatec.adressebok.domain.model.Kontakt;
import net.innovatec.adressebok.domain.model.TelefonType;

public interface AdressebokRepo {

    public List<Adressebok> hentAlleAdresseboker();

    public Adressebok opprettAdressebok();

    public Adressebok hentAdressebok(String adressebokId);

    public void slettAdressebok(String adressebokId);

    public List<Kontakt> hentKontakter(String adressebokId);

    public Kontakt opprettKontakt(String adressebokId, String fornavn, String etternavn);

    public Kontakt hentKontakt(String adressebokId, String kontaktId);

    public Kontakt oppdaterKontakt(String adressebokId, String kontaktId, AdresseType adresseType, String gatenavn,
            String gatenummer, String postnummer,
            String by,
            String land, String epostAdresse, TelefonType telefonType, String telefonnummer);

    public void slettKontakt(String adressebokId, String kontaktId);
}
