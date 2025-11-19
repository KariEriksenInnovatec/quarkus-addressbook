package net.innovatec.adressebok.domain.model;

import java.util.UUID;

public class KontaktId extends DomeneId {
	public KontaktId() {
		super();
	}

	public KontaktId(String uuid) {
		super(uuid);
	}

	public KontaktId(UUID uuid) {
		super(uuid);
	}

}
