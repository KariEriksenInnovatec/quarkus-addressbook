package net.innovatec.adressebok.domain;

import java.util.List;

import net.innovatec.adressebok.domain.model.Adressebok;
import net.innovatec.adressebok.domain.model.AdressebokId;
import net.innovatec.adressebok.domain.model.Kontakt;
import net.innovatec.adressebok.domain.model.KontaktData;
import net.innovatec.adressebok.domain.model.KontaktId;
import net.innovatec.adressebok.domain.model.Navn;

public interface AdressebokService {
    public AdressebokId opprettAdressebok();
    
    public AdressebokId importerAdressebok(Adressebok bok);
    
    public Adressebok hentAdressebok(AdressebokId id);
    
    public void slettAdressebok(AdressebokId id);
    
    public KontaktId opprettOgLeggTilKontakt(AdressebokId id, Navn navn);

    public KontaktId opprettOgLeggTilKontakt(AdressebokId adressebokId, KontaktData data);

    public Kontakt hentKontakt(AdressebokId bokId, KontaktId kontaktId);
    
    public void slettKontakt(AdressebokId bokId, KontaktId kontaktId);
    
    public List<Kontakt> søkKontakt(AdressebokId bokId, String kriterie);
}
