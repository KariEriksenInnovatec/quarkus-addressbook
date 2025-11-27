package net.innovatec.adressebok.domain.model;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class Kontakt {
    private KontaktId id = null;
    private KontaktData data;

    public Kontakt(KontaktId id, Navn navn) {
        this.id = id;
        this.data = new KontaktData(navn, Set.of(), Set.of(), Set.of());
    }

    public Kontakt(Navn navn) {
        this.id = new KontaktId(); 
        this.data = new KontaktData(navn, Set.of(), Set.of(), Set.of());
    }
    
    public Kontakt(KontaktId id, KontaktData data) {
        this.id = id;
        this.data = data;
    }

    private Kontakt(KontaktData data) {
        this.id = new KontaktId(); 
        this.data = data;
    }
    
    public static final Kontakt createNewKontakt(KontaktData data) {
        Kontakt nyKontakt = new Kontakt(data.navn());
        for(Adresse element: data.adresser()) {
            nyKontakt.leggTilAdresse(element);
        }
        for(Epost element: data.epostadresser()) {
            nyKontakt.leggTilEpostadresse(element);
        }
        for(Telefon element: data.telefonnumre()) {
            nyKontakt.leggTilTelefonnummer(element);
        }
        
        return new Kontakt(data);
    }

    public static final Kontakt createNewKontakt(KontaktId id, KontaktData data) {
        Kontakt nyKontakt = new Kontakt(id, data.navn());
        for(Adresse element: data.adresser()) {
            nyKontakt.leggTilAdresse(element);
        }
        for(Epost element: data.epostadresser()) {
            nyKontakt.leggTilEpostadresse(element);
        }
        for(Telefon element: data.telefonnumre()) {
            nyKontakt.leggTilTelefonnummer(element);
        }
        
        return new Kontakt(data);
    }
    
    public KontaktId hentId() {
        return id;
    }

    public KontaktData hentData() {
        return data;
    }
    
    public Navn hentNavn() {
        return data.navn();
    }

    public void endreNavn(Navn navn) {
        this.data = data.withNavn(navn);
    }

    public Adresse opprettAdresse(AdresseType type, String gatenavn, String gatenummer, String postnummer, String by, String landskode) {
        return new Adresse(type, gatenavn, gatenummer, postnummer, by, landskode);
    }

    public void leggTilAdresse(Adresse adresse) {
        if (data.adresser().size() >= 2) throw new DomeneException("Man kan ikke legge til mer enn 2 adresser til kontakt!");                
        this.data = data.withAddedAdresse(adresse);
    }

    public List<Adresse> hentAdresser() {
        return data.adresser().stream().toList();
    }

    public Boolean slettAdresse(Adresse adresse) {
        Set<Adresse> updatedAdresser = new HashSet<>(data.adresser());
        boolean removed = updatedAdresser.remove(adresse);
        this.data = data.withAdresser(Set.copyOf(updatedAdresser));
        return removed;
    }

    public Epost opprettEpostadresse(String epostadresse) {
        return new Epost(epostadresse);
    }

    public void leggTilEpostadresse(Epost epost) {
        if (data.epostadresser().size() >= 10) throw new DomeneException("Man kan ikke legge til mer enn 10 epostadresser til kontakt!");
        this.data = data.withAddedEpost(epost);
    }

    public List<Epost> hentEpostadresser() {
        return data.epostadresser().stream().toList();
    }

    public Boolean slettEpostadresse(Epost epost) {
        Set<Epost> updatedEpostadresser = new HashSet<>(data.epostadresser());
        boolean removed = updatedEpostadresser.remove(epost);
        this.data = data.withEpostadresser(Set.copyOf(updatedEpostadresser));
        return removed;
    }

    public Telefon opprettTelefonnummer(TelefonType type, String landkode, String nummer) {
        return new Telefon(type, landkode, nummer);
    }

    public void leggTilTelefonnummer(Telefon telefon) {
        if (data.telefonnumre().size() >= 10) throw new DomeneException("Man kan ikke legge til mer enn 10 telefonnummere til kontakt!");        
        this.data = data.withAddedTelefon(telefon);
    }

    public List<Telefon> hentTelefonnummerer() {
        return data.telefonnumre().stream().toList();
    }

    public Boolean slettTelefon(Telefon telefon) {
        Set<Telefon> updatedTelefonnummerer = new HashSet<>(data.telefonnumre());
        boolean removed = updatedTelefonnummerer.remove(telefon);
        this.data = data.withTelefonnummerer(Set.copyOf(updatedTelefonnummerer));
        return removed;
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
        Kontakt other = (Kontakt) obj;
        return Objects.equals(id, other.id);
    }
}
