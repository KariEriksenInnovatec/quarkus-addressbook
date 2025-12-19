package net.innovatec.adressebok.infrastructure.persistence.converters;

import net.innovatec.adressebok.domain.model.KontaktId;

public class KontaktIdConverter extends DomeneIdConverter<KontaktId> {
    private static final long serialVersionUID = 8948965001287732682L;

    public KontaktIdConverter() {
        super(KontaktId.class);
    }
}
