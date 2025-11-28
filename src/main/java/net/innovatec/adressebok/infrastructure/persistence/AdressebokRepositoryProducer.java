package net.innovatec.adressebok.infrastructure.persistence;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import net.innovatec.adressebok.domain.AdressebokRepository;

@ApplicationScoped
public class AdressebokRepositoryProducer {

    @Inject
    @ConfigProperty(name = "repository.type", defaultValue = "in-memory")
    String repositoryType;

    @Produces
    public AdressebokRepository produceRepository() {
        if ("database".equals(repositoryType)) {
            return new DatabaseAdressebokRepository();
        } else {
            return new InMemoryAdressebokRepository();
        }
    }
}
