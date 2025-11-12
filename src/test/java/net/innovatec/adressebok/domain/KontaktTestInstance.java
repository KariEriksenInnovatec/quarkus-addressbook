package net.innovatec.adressebok.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class KontaktTestInstance {

    private Kontakt kontakt;
    private KontaktData kontaktData;

    @BeforeAll
    void initiereData() {

        Navn navn = new Navn("Ali", "Smith");
        Adresse adresse = new Adresse(AdresseType.PRIVAT, "PRIVAT", "First Street", "50", "London", "England");
        Epost epost = new Epost("ali_smith@hotmail.com");
        Telefon telefon = new Telefon(TelefonType.PRIVAT, "+442071234567");

        kontaktData = new KontaktData(
                navn,
                List.of(adresse),
                List.of(epost),
                List.of(telefon));

        kontakt = new Kontakt(new KontaktId(1), kontaktData);

    }

    @Test
    void testOpprettAdresse() {

        assertEquals("Ali", kontakt.hentKontaktData().navn().fornavn());
        assertEquals("Ali", kontaktData.navn().fornavn());
    }
}
