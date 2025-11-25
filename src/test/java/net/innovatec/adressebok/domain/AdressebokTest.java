package net.innovatec.adressebok.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AdressebokTest {

    private Adressebok adressebok;

    @BeforeAll
    void initiereData() {

        // AdressebokId id = new AdressebokId(1)
        adressebok = new Adressebok(null);
    }

    @Test
    void testOpprettKontakt_VellykketUUID() {

    }

    @Test
    void testLeggTilKontakt_IkkeLovMedIdentiskeNavn() {

        KontaktId testId = new KontaktId(UUID.randomUUID());
        Navn navn = new Navn("Jane", "Smith");
        Kontakt kontakt = new Kontakt(testId, navn);
        adressebok.leggTilKontakt(kontakt);

        // Forsøk å opprette en ny kontakt med samme navn, skal kaste exception
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            adressebok.leggTilKontakt(kontakt);
        });

        assertEquals("En kontakt med navnet Jane Smith finnes allerede.", exception.getMessage());
    }

    @Test
    void testLeggTilKontakt_IkkeOppgittNavnEllerId() {

        KontaktId testId = new KontaktId(UUID.randomUUID());
        Kontakt kontakt = new Kontakt(testId, null);

        // Forsøk å opprette en ny kontakt uten navn, skal kaste exception
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            adressebok.leggTilKontakt(kontakt);
        });

        assertEquals("KontaktId og navn er påkrevd for å legge til en eksisterende kontakt.", exception.getMessage());
    }

    @Test
    void testLeggTilKontakt_Vellykket() {

        KontaktId testId = new KontaktId(UUID.randomUUID());
        Navn navn = new Navn("Ali", "Smith");
        Kontakt kontakt = new Kontakt(testId, navn);
        adressebok.leggTilKontakt(kontakt);

        assertNotNull(adressebok.hentKontakter());
        assertEquals("Ali", adressebok.hentKontakt(testId).hentNavn().fornavn());
    }

    @Test
    void testHentKontakt_IdKanIkkeVæreNull() {

        // Forsøk å hente en ny kontakt med id = null, skal kaste exception
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            adressebok.hentKontakt(null);
        });
        assertEquals("KontaktId kan ikke være null.", exception.getMessage());
    }

    @Test
    void testHentKontakt_KontaktFinnesIkke() {

        KontaktId testId = new KontaktId(UUID.randomUUID());

        // Forsøk å hente en ny kontakt med id = null, skal kaste exception
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            adressebok.hentKontakt(testId);
        });
        assertEquals("Ingen kontakt med id " + testId + " ble funnet.", exception.getMessage());
    }

    @Test
    void testHentKontakt_Vellykket() {

        KontaktId testId = new KontaktId(UUID.randomUUID());
        Navn navn = new Navn("Alice", "Smith");
        Kontakt kontakt = new Kontakt(testId, navn);
        adressebok.leggTilKontakt(kontakt);

        assertNotNull(adressebok.hentKontakt(testId));
    }

    @Test
    void testOppdatereKontakt_IdKanIkkeVæreNull() {

        // Forsøk å hente en ny kontakt med id = null, skal kaste exception
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            adressebok.hentKontakt(null);
        });
        assertEquals("KontaktId kan ikke være null.", exception.getMessage());
    }

    @Test
    void testOppdatereKontakt_DuplisertNavn() {

        // Opprett to kontakter
        KontaktId testId = new KontaktId(UUID.randomUUID());
        Kontakt kontakt1 = new Kontakt(testId, new Navn("Tom", "Smith"));
        Kontakt kontakt2 = new Kontakt(new KontaktId(UUID.randomUUID()), new Navn("John", "Doe"));
        adressebok.leggTilKontakt(kontakt1);
        adressebok.leggTilKontakt(kontakt2);

        // Forsøk å endre kontakt1 sitt navn til samme som kontakt2
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            adressebok.oppdatereKontakt(testId, new Navn("John", "Doe"));
        });

        assertEquals("En annen kontakt har allerede navnet John Doe", exception.getMessage());
    }

    @Test
    void testOppdatereKontakt_Vellykket() {

        KontaktId testId = new KontaktId(UUID.randomUUID());
        Kontakt kontakt = new Kontakt(testId, new Navn("June", "McDonald"));
        adressebok.leggTilKontakt(kontakt);

        adressebok.oppdatereKontakt(testId, new Navn("Eli", "West"));
        assertEquals("Eli", adressebok.hentKontakt(testId).hentNavn().fornavn());
        assertEquals("West", adressebok.hentKontakt(testId).hentNavn().etternavn());
    }

    @Test
    void testSlettKontakt_KontaktFinnesIkke() {

        KontaktId testId = new KontaktId(UUID.randomUUID());
        Kontakt kontakt = new Kontakt(testId, new Navn("James", "Watson"));
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            adressebok.slettKontakt(kontakt);
        });

        assertEquals("Kan ikke slette kontakt, den finnes ikke i kontaktlisten.", exception.getMessage());
    }

    @Test
    void testSlettKontakt_Vellykket() {

        KontaktId testId = new KontaktId(UUID.randomUUID());
        Kontakt kontakt = new Kontakt(testId, new Navn("Sharon", "Jones"));
        adressebok.leggTilKontakt(kontakt);

        // Verifiser at kontakten finnes
        assertNotNull(adressebok.hentKontakt(testId));

        adressebok.slettKontakt(kontakt);

        // Verifiser at hentKontakt nå kaster exception
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            adressebok.hentKontakt(testId);
        });

        assertEquals("Ingen kontakt med id " + testId + " ble funnet.", exception.getMessage());
    }

    @Test
    void testSøkKontakt_KriterieKanIkkeVæreNull() {

    }

    @Test
    void testSøkKontakt_VellykketSøkMedKriterie() {

        KontaktId testId = new KontaktId(UUID.randomUUID());
        Kontakt kontakt = new Kontakt(testId, new Navn("Maria", "Stone"));
        adressebok.leggTilKontakt(kontakt);

        assertEquals("Maria Stone", adressebok.søkKontakt(new Navn("Maria", "Stone")));
    }

    @Test
    void testSøkKontakt_NavnKanIkkeVæreNull() {

    }

    @Test
    void testSøkKontakt_FinnerIkkeKontaktPåNavn() {

    }

    @Test
    void testSøkKontakt_VellykketSøkPåNavn() {

    }
}
