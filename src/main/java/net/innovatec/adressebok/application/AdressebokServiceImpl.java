package net.innovatec.adressebok.application;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import net.innovatec.adressebok.domain.AdressebokRepo;
import net.innovatec.adressebok.domain.AdressebokService;
import net.innovatec.adressebok.domain.model.Adressebok;

@ApplicationScoped
public class AdressebokServiceImpl implements AdressebokService {

    // Orchestrerer operasjoner
    // Kaller Repo som gjør det faktiske arbeidet

    @Inject
    AdressebokRepo repo;

    @Override
    public List<Adressebok> hentAlleAdresseboker() {
        return repo.hentAlleAdresseboker();
    }

    @Override
    public Adressebok opprettAdressebok() {

        return repo.opprettAdressebok();
    }

    @Override
    public Adressebok hentAdressebok(String adressebokId) {

        return repo.hentAdressebok(adressebokId);
    }

    @Override
    public void slettAdressebok(String adressebokId) {
        repo.slettAdressebok(adressebokId);
    }
}
