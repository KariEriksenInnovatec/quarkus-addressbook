package net.innovatec.adressebok.domain;

import java.util.List;

import net.innovatec.adressebok.domain.model.Adressebok;
import net.innovatec.adressebok.domain.model.Kontakt;

public interface AdressebokService {

    public List<Adressebok> hentAlleAdresseboker();

    public Adressebok opprettAdressebok();

    public Adressebok hentAdressebok(String adressebokId);

    public void slettAdressebok(String adressebokId);

    public List<Kontakt> hentKontakter(String adressebokId);

    public Kontakt opprettKontakt(String adressebokId, String fornavn, String etternavn);

    public Kontakt hentKontakt(String adressebokId, String kontaktId);

    public Kontakt oppdaterKontakt(String adressebokId, String kontaktId, Kontakt oppdatertKontaktData);

    public void slettKontakt(String adressebokId, String kontaktId);

}
