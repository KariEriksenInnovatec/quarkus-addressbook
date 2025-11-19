package net.innovatec.adressebok.infrastructure.persistence;

import net.innovatec.adressebok.domain.model.Adressebok;
import net.innovatec.adressebok.domain.model.AdressebokId;

public interface AdressebokRepository {
	public Adressebok opprettAdressBok();
	
	public Adressebok hentAdressebok(String uuid);

	public Adressebok hentAdressebok(AdressebokId adressebokId);

	public Boolean slettAdressebok(String uuid);

	public Boolean slettAdressebok(AdressebokId adressebokId);	
}
