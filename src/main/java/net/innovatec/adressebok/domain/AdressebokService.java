package net.innovatec.adressebok.domain;

import java.util.List;

import net.innovatec.adressebok.domain.model.Adressebok;

public interface AdressebokService {

    public List<Adressebok> hentAlleAdresseboker();

    public Adressebok opprettAdressebok();

    public Adressebok hentAdressebok(String adressebokId);

    public void slettAdressebok(String adressebokId);
}
