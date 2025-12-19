
package net.innovatec.adressebok.infrastructure.persistence;

import java.util.HashMap;
import java.util.Map;

import net.innovatec.adressebok.domain.AdressebokRepository;
import net.innovatec.adressebok.domain.model.Adressebok;
import net.innovatec.adressebok.domain.model.AdressebokId;
import net.innovatec.adressebok.domain.model.DomeneException;
import net.innovatec.adressebok.domain.model.IkkeFunnetDomeneException;

public final class InMemoryAdressebokRepository implements AdressebokRepository {
	private Map<AdressebokId, Adressebok> adressebøker = new HashMap<AdressebokId, Adressebok>();

	public Adressebok opprettAdressebok() {
		Adressebok adressebok = new Adressebok();
		adressebøker.put(adressebok.hentId(), adressebok);
		return adressebok;
	}
	
	public AdressebokId lagreAdressebok(Adressebok bok) {
	    adressebøker.put(bok.hentId(), bok);
	    return bok.hentId();
	}
	
	public Adressebok hentAdressebok(String uuid) {
		AdressebokId adressebokId = new AdressebokId(uuid);
		return hentAdressebok(adressebokId);
	}

	public Adressebok hentAdressebok(AdressebokId adressebokId) {
        if(!adressebøker.containsKey(adressebokId)) throw new IkkeFunnetDomeneException("Adressebok eksisterer ikke!");
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
