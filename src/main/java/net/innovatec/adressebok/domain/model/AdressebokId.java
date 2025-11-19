package net.innovatec.adressebok.domain.model;

import java.util.UUID;

public class AdressebokId extends DomeneId {
	public AdressebokId() {
		super();
	}

	public AdressebokId(String uuid) {
		super(uuid);
	}

	public AdressebokId(UUID uuid) {
		super(uuid);
	}	
}
