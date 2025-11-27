package net.innovatec.adressebok.domain;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import net.innovatec.adressebok.domain.model.AdressebokId;

@ApplicationScoped
public class AdressebokServiceImpl implements AdressebokService {

    @Inject
    AdressebokRepo repo;

    @Override
    public AdressebokId opprettAdressebok() {

        // Orchestrerer operasjoner
        // Kaller Repo som gjør det faktiske arbeidet
        return repo.opprettAdressebok();
    }
}
