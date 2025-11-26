package net.innovatec.adressebok.domain.model;

public record Adresse(AdresseType adresseType, String gatenavn, String gatenummer, String postnummer, String by,
        String land) {

}