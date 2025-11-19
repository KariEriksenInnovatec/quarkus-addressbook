package net.innovatec.adressebok.domain.model;

import java.util.Objects;
import java.util.UUID;

public abstract class DomeneId {
	private UUID id = null;
	
	protected DomeneId() {
		id = UUID.randomUUID();
	}
	
	protected DomeneId(String uuid) {
		id = UUID.fromString(uuid);
	}
	
	protected DomeneId(UUID uuid) {
		id = uuid;
	}

	public UUID getId() {
		return id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DomeneId other = (DomeneId) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "DomeneId [id=" + id + "]";
	}
	
}
