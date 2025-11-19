
package net.innovatec.adressebok.infrastructure.persistence;

import java.util.HashMap;
import java.util.Map;

import io.quarkus.arc.profile.IfBuildProfile;
import jakarta.enterprise.context.ApplicationScoped;
import net.innovatec.adressebok.domain.model.Adressebok;
import net.innovatec.adressebok.domain.model.AdressebokId;

@IfBuildProfile("dev")
@ApplicationScoped
public final class InMemoryAdressebokRepository implements AdressebokRepository {
	private Map<AdressebokId, Adressebok> adressebøker = new HashMap<AdressebokId, Adressebok>();

	public Adressebok opprettAdressBok() {
		Adressebok adressebok = new Adressebok();
		adressebøker.put(adressebok.getId(), adressebok);
		return adressebok;
	}
	
	public Adressebok hentAdressebok(String uuid) {
		AdressebokId adressebokId = new AdressebokId(uuid);
		return hentAdressebok(adressebokId);
	}

	public Adressebok hentAdressebok(AdressebokId adressebokId) {
		if(!adressebøker.containsKey(adressebokId)) throw new RuntimeException("Adressebok eksisterer ikke!");
		return adressebøker.get(adressebokId);
	}

	public Boolean slettAdressebok(String uuid) {
		AdressebokId adressebokId = new AdressebokId(uuid);
		return slettAdressebok(adressebokId);
	}

	public Boolean slettAdressebok(AdressebokId adressebokId) {
		return adressebøker.remove(adressebokId) != null;
	}
		
}
