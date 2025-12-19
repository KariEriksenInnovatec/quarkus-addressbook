package net.innovatec.adressebok.infrastructure.persistence;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jooq.DSLContext;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import net.innovatec.adressebok.domain.AdressebokRepository;

@ApplicationScoped
public class AdressebokRepositoryProducer {
    @Inject
    DSLContext ctx;
    
    @Inject
    @ConfigProperty(name = "repository.type", defaultValue = "in-memory")
    String repositoryType;

    @Produces
    @ApplicationScoped
    public AdressebokRepository produceRepository() {
        Log.info("Producing AdressebokRepository");
        if ("database".equals(repositoryType)) {
            Log.info("Using the database implementation");
            return new DatabaseAdressebokRepository(ctx);
        } else {
            Log.info("Using the in-memory implementation");
            return new InMemoryAdressebokRepository();
        }
    }
}
