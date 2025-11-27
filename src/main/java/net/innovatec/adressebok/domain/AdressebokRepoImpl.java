package net.innovatec.adressebok.domain;

import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;
import net.innovatec.adressebok.domain.model.AdressebokId;

@ApplicationScoped
public class AdressebokRepoImpl implements AdressebokRepo {

    @Override
    public AdressebokId opprettAdressebok() {
        
        // Generer en ny ID
        // Repo eier ID; unngår dupliketer, skal lagrets i database senere
        return new AdressebokId(UUID.randomUUID());
    }
}
