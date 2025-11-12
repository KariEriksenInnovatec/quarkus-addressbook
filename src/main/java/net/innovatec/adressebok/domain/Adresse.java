package net.innovatec.adressebok.domain;

public record Adresse(AdresseType adresseType, String gatenavn, String gatenummer, String postnummer, String by,
        String land) {

}