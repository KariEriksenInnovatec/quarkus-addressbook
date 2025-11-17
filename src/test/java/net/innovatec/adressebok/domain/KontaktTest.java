package net.innovatec.adressebok.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class KontaktTest {

    private Kontakt kontakt;

    @BeforeAll
    void initiereData() {

        /*
         * Navn navn = new Navn("Ali", "Smith");
         * Adresse adresse = new Adresse(AdresseType.PRIVAT, "PRIVAT", "First Street",
         * "50", "London", "England");
         * Epost epost = new Epost("ali_smith@hotmail.com");
         * Telefon telefon = new Telefon(TelefonType.PRIVAT, "+442071234567");
         * 
         * kontakt = new Kontakt(new KontaktId(1), navn, adresse, epost, telefon);
         */

        Navn navn = new Navn("Ali", "Smith");
        List<Adresse> adresser = new ArrayList<>();
        List<Epost> epostListe = new ArrayList<>();
        List<Telefon> telefonListe = new ArrayList<>();

        kontakt = new Kontakt(new KontaktId(1), navn, adresser, epostListe, telefonListe);
    }

    @Test
    void testOpprettAdresse_IkkeLovMedMerEnn2Adresser() {

        // Opprett første adresse
        kontakt.opprettAdresse(AdresseType.PRIVAT, "First Street", "50", "12345", "London",
                "England");
        // Opprett andre adresse
        kontakt.opprettAdresse(AdresseType.JOBB, "Second Avenue", "100", "54321", "Manchester",
                "England");
        // Forsøk å opprette tredje adresse, skal kaste RuntimeException
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            kontakt.opprettAdresse(AdresseType.PRIVAT, "Third Road", "200", "99999", "Birmingham", "England");
        });
        // Verifiser feilmeldingen
        assertEquals("Ikke lov å opprette mer enn 2 adresser.", exception.getMessage());
    }

    @Test
    void testOpprettAdresse_IkkeLovMed2AdresserAvSammeType() {

        // Opprett en adresse med adresseType lik PRIVAT
        kontakt.opprettAdresse(AdresseType.PRIVAT, "First Street", "50", "12345", "London",
                "England");

        // Forsøk å opprette enda en adresse med adresseType lik PRIVAT, skal kaste
        // RuntimeException
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            kontakt.opprettAdresse(AdresseType.PRIVAT, "Second Avenue", "100", "54321", "Manchester",
                    "England");
        });
        // Verifiser feilmeldingen
        assertEquals("Ikke lov med to adresser av samme type.", exception.getMessage());
    }

    @Test
    void testOpprettAdresse_VellykketOpprettelse() {

        // Opprett første adresse
        Adresse adresse = kontakt.opprettAdresse(AdresseType.PRIVAT, "First Street", "50", "12345", "London",
                "England");

        // Verifiser at adressen ble opprettet og returnert
        assertNotNull(adresse);
        assertEquals(AdresseType.PRIVAT, adresse.adresseType());
        assertEquals("First Street", adresse.gatenavn());
    }

    // Teste sletting av adresse?

    @Test
    void testOpprettEpost_IkkeLovMedMerEnn3Epostadresser() {

        kontakt.opprettEpost("test@testing.com");
        kontakt.opprettEpost("test2@testing.com");
        kontakt.opprettEpost("test3@testing.com");

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            kontakt.opprettEpost("test4@testing.com");
        });

        assertEquals("Ikke lov å opprette mer enn 3 e-post adresser.", exception.getMessage());
    }

    @Test
    void testOpprettEpost_VellykketOpprettelse() {

        Epost epost = kontakt.opprettEpost("test@testing.com");
        assertNotNull(epost);
        assertEquals("test@testing.com", epost.epostAdresse());
    }

    // Teste sletting av epost?

    @Test
    void testOpprettTelefon_IkkeLovMedMerEnn10Telefonnummer() {

        // Ikke lov å opprette mer enn 10 telefonnummer.
        kontakt.opprettTelefon(TelefonType.PRIVAT, "+442071234561"); // 1
        kontakt.opprettTelefon(TelefonType.PRIVAT, "+442071234562"); // 2
        kontakt.opprettTelefon(TelefonType.PRIVAT, "+442071234563"); // 3
        kontakt.opprettTelefon(TelefonType.PRIVAT, "+442071234564"); // 4
        kontakt.opprettTelefon(TelefonType.PRIVAT, "+442071234565"); // 5
        kontakt.opprettTelefon(TelefonType.PRIVAT, "+442071234566"); // 6
        kontakt.opprettTelefon(TelefonType.PRIVAT, "+442071234567"); // 7
        kontakt.opprettTelefon(TelefonType.PRIVAT, "+442071234568"); // 8
        kontakt.opprettTelefon(TelefonType.PRIVAT, "+442071234569"); // 9
        kontakt.opprettTelefon(TelefonType.PRIVAT, "+442071234510"); // 10

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            kontakt.opprettTelefon(TelefonType.PRIVAT, "+442071234511");
        });

        assertEquals("Ikke lov å opprette mer enn 10 telefonnummer.", exception.getMessage());
    }

    @Test
    void testOpprettTelefon_VellykketOpprettelse() {

        Telefon telefon = kontakt.opprettTelefon(TelefonType.PRIVAT, "+442071234561");
        assertNotNull(telefon);
        assertEquals(TelefonType.PRIVAT, telefon.telefonType());
        assertEquals("+442071234561", telefon.telefonnummer());

    }

    // Teste sletting av telefon?
}
