package net.innovatec.adressebok.infrastructure.persistence;

import javax.sql.DataSource;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

// @ApplicationScoped
public class DSLContextProducer {

    // @Produces
    // @ApplicationScoped
    public DSLContext createDSLContext(DataSource dataSource) {
        // Configure and return your DSLContext here
        return DSL.using(dataSource, SQLDialect.POSTGRES);
    }
}
