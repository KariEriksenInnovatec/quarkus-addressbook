package net.innovatec.adressebok.infrastructure.persistence;

import jakarta.enterprise.context.ApplicationScoped;
import net.innovatec.adressebok.domain.AdressebokRepository;
import net.innovatec.adressebok.domain.model.Adressebok;
import net.innovatec.adressebok.domain.model.AdressebokId;

// @ApplicationScoped
public class DatabaseAdressebokRepository implements AdressebokRepository {

    public DatabaseAdressebokRepository() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public Adressebok opprettAdressBok() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AdressebokId leggTilAdressebok(Adressebok bok) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Adressebok hentAdressebok(String uuid) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Adressebok hentAdressebok(AdressebokId adressebokId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean slettAdressebok(String uuid) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean slettAdressebok(AdressebokId adressebokId) {
        // TODO Auto-generated method stub
        return null;
    }

}
