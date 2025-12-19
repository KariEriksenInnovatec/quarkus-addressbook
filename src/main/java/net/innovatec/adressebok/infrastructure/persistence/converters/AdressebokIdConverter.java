package net.innovatec.adressebok.infrastructure.persistence.converters;

import net.innovatec.adressebok.domain.model.AdressebokId;

public class AdressebokIdConverter extends DomeneIdConverter<AdressebokId> {
    private static final long serialVersionUID = 2950428667716927908L;

    public AdressebokIdConverter() {
        super(AdressebokId.class);
    }
}
