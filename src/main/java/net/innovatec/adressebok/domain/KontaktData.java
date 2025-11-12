package net.innovatec.adressebok.domain;

import java.util.List;

public record KontaktData(Navn navn, List<Adresse> adresser, List<Epost> epost, List<Telefon> telefon) {

}
