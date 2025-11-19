package net.innovatec.adressebok.domain.model;

public record Navn(String fornavn, String etternavn) {
	public Navn {
		if( fornavn == null || fornavn.isEmpty() || etternavn == null || etternavn.isEmpty()) throw new DomeneException("Både fornavn og etternavn må ha verdier!");
		if( fornavn.length() > 100) throw new DomeneException("Fornavn kan ikke være mer enn 100 tegn.");
		if( etternavn.length() > 50) throw new DomeneException("Etternavn kan ikke være mer enn 50 tegn.");		
	}
	
	public String fullnavn() {
		return fornavn + " " + etternavn;
	}
}
