package net.innovatec.adressebok.domain.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public record KontaktData(
    Navn navn,
    Set<Adresse> adresser,
    Set<Epost> epostadresser,
    Set<Telefon> telefonnumre
) {
    public KontaktData {
        navn = Objects.requireNonNull(navn, "navn must not be null");
        adresser = adresser == null ? Set.of() : Set.copyOf(adresser);
        epostadresser = epostadresser == null ? Set.of() : Set.copyOf(epostadresser);
        telefonnumre = telefonnumre == null ? Set.of() : Set.copyOf(telefonnumre);
    }

    public KontaktData withNavn(Navn nyNavn) {
        return new KontaktData(nyNavn, this.adresser, this.epostadresser, this.telefonnumre);
    }

    public KontaktData withAddedAdresse(Adresse adresse) {
        Objects.requireNonNull(adresse);
        Set<Adresse> copy = new HashSet<>(this.adresser);
        copy.add(adresse);
        return new KontaktData(this.navn, Set.copyOf(copy), this.epostadresser, this.telefonnumre);
    }

    public KontaktData withAddedEpost(Epost epost) {
        Objects.requireNonNull(epost);
        Set<Epost> copy = new HashSet<>(this.epostadresser);
        copy.add(epost);
        return new KontaktData(this.navn, this.adresser, Set.copyOf(copy), this.telefonnumre);
    }

    public KontaktData withAddedTelefon(Telefon telefon) {
        Objects.requireNonNull(telefon);
        Set<Telefon> copy = new HashSet<>(this.telefonnumre);
        copy.add(telefon);
        return new KontaktData(this.navn, this.adresser, this.epostadresser, Set.copyOf(copy));
    }

    public KontaktData withAdresser(Set<Adresse> nyeAdresser) {
        return new KontaktData(this.navn, Set.copyOf(nyeAdresser), this.epostadresser, this.telefonnumre);
    }

    public KontaktData withEpostadresser(Set<Epost> nyeEpostadresser) {
        return new KontaktData(this.navn, this.adresser, Set.copyOf(nyeEpostadresser), this.telefonnumre);
    }

    public KontaktData withTelefonnummerer(Set<Telefon> nyeTelefonnummerer) {
        return new KontaktData(this.navn, this.adresser, this.epostadresser, Set.copyOf(nyeTelefonnummerer));
    }
}
