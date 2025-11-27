package net.innovatec.adressebok.domain.model;

public record Navn(String fornavn, String etternavn) {	
	public String fullnavn() {
		return fornavn + " " + etternavn;
	}
}
