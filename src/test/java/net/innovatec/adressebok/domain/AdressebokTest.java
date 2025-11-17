package net.innovatec.adressebok.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AdressebokTest {

    private Adressebok adressebok;

    @BeforeAll
    void initiereData() {

        // AdressebokId id = new AdressebokId(1)
        adressebok = new Adressebok(null, new ArrayList<>());
    }

    @Test
    void testOpprettKontakt_IkkeLovMedIdentiskeNavn() {

        Navn navn = new Navn("Ali", "Smith");
        adressebok.opprettKontakt(new KontaktId(1), navn);

        // Forsøk å opprette en ny kontakt med samme navn, skal kaste exception
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            adressebok.opprettKontakt(new KontaktId(2), navn);
        });

        assertEquals("En kontakt med navnet Ali Smith finnes allerede.", exception.getMessage());
    }

    @Test
    void testOpprettKontakt_VellykketOpprettelse() {

        Navn navn = new Navn("Ali", "Smith");
        adressebok.opprettKontakt(new KontaktId(1), navn);

        assertNotNull(adressebok.hentKontakter());
        assertEquals("Ali", adressebok.hentKontakter().get(0).hentNavn().fornavn()); //må implementerer hentKontakt
    }
}
