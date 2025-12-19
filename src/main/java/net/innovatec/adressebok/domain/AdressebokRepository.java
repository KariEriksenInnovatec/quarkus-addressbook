package net.innovatec.adressebok.domain;

import net.innovatec.adressebok.domain.model.Adressebok;
import net.innovatec.adressebok.domain.model.AdressebokId;

public interface AdressebokRepository {
	public Adressebok opprettAdressebok();

	public AdressebokId lagreAdressebok(Adressebok bok);
	
	public Adressebok hentAdressebok(String uuid);

	public Adressebok hentAdressebok(AdressebokId adressebokId);

	public Boolean slettAdressebok(String uuid);

	public Boolean slettAdressebok(AdressebokId adressebokId);
}
