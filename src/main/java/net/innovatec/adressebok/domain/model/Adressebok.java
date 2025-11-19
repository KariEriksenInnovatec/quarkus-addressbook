package net.innovatec.adressebok.domain.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Adressebok {
	private AdressebokId id = null;
	private Map<KontaktId, KontaktData> kontakter = new HashMap<>();

	public Adressebok() {
		id = new AdressebokId();
	}
	
    public Adressebok(AdressebokId id) {
        this.id = id;
    }

    public AdressebokId getId() {
		return id;
	}
        
    public void leggTilKontakt(KontaktId id, KontaktData data) {
        if( kontakter.size() > 100) throw new DomeneException("Man kan ikke ha mer enn 100 kontakter i adressebok!");
        if( finnesNavn(data.navn())) throw new DomeneException("Kan ikke legge til kontakt med navn som allerede finnes!");
        kontakter.put(id, data);
    }

    public void leggTilKontakt(Kontakt kontakt) {
        leggTilKontakt(kontakt.hentId(), kontakt.hentData());
    }

    private Boolean finnesNavn(Navn navn) {
		return hentKontakter().stream().anyMatch(k -> k.hentNavn().equals(navn));
	}
	
	public List<Kontakt> hentKontakter() {
		return kontakter.entrySet().stream().map(entry -> new Kontakt(entry.getKey(), entry.getValue())).toList();
	}
			
	public Kontakt henteKontakt(KontaktId id) {
        if( !kontakter.keySet().contains(id)) throw new DomeneException("Angitt KontaktId ikke funnet!");
		return new Kontakt(id, kontakter.get(id));
	}

	public void oppdatereKontakt(KontaktId id, KontaktData data) {
	    if( !kontakter.keySet().contains(id)) throw new DomeneException("Angitt KontaktId ikke funet! Oppdatering feilet!");
	    kontakter.put(id, data);
	}

	public Boolean slettKontakt(KontaktId id) {
		return kontakter.remove(id) != null;
	}

	public List<Kontakt> søkKontakt(String kriterie) {
		List<Kontakt> result = new ArrayList<Kontakt>();
		for (Kontakt kontakt: hentKontakter()) {
			if(kontakt.hentNavn().fullnavn().matches(kriterie)) {
				result.add(kontakt);
			}
		}
		return result;
	}

	public List<Kontakt> søkFornavn(String fornavn) {
		return søkKontakt("^" + fornavn + ".*");
	}

	public List<Kontakt> søkEtternavn(String etternavn) {
		return søkKontakt(".*" + etternavn + "$");
	}

}
