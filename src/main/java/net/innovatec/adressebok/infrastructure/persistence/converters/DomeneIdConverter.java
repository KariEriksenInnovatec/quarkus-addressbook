package net.innovatec.adressebok.infrastructure.persistence.converters;

import java.util.UUID;
import org.jooq.Converter;
import net.innovatec.adressebok.domain.model.DomeneId;

public abstract class DomeneIdConverter<T extends DomeneId> implements Converter<UUID, T> {
    private static final long serialVersionUID = 1L;
    private final Class<T> type;

    protected DomeneIdConverter(Class<T> type) {
        this.type = type;
    }

    @Override
    public T from(UUID databaseObject) {
        if (databaseObject == null) {
            return null;
        }
        try {
            return type.getConstructor(UUID.class).newInstance(databaseObject);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert UUID to " + type.getName(), e);
        }
    }

    @Override
    public UUID to(T userObject) {
        if (userObject == null) {
            return null;
        }
        return userObject.getId();
    }

    @Override
    public Class<UUID> fromType() {
        return UUID.class;
    }

    @Override
    public Class<T> toType() {
        return type;
    }
}
