package net.innovatec.adressebok.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class KontaktTest {
    static Kontakt mickeyMouse = null;
    static Kontakt minnieMouse = null;

    @BeforeAll
    static void init() {
        mickeyMouse = Kontakt.createNewKontakt(new Navn("Mickey", "Mouse"));
        opprettOgLeggTilAdresse(mickeyMouse, AdresseType.JOBB, "South Buena Vista Street", "500", "91521", "Burbank", "US");
        opprettOgLeggTilAdresse(mickeyMouse, AdresseType.PRIVAT, "P.O. Box 10040", "", "32830-0040", "Lake Buena Vista", "US");
        opprettOgLeggTilEpostadresse(mickeyMouse, "mickey.mouse@disney.com");
        opprettOgLeggTilTelefonnummer(mickeyMouse, TelefonType.PRIVAT, "1", "(999) 999-9999");
    }

    @BeforeEach
    void initMinnieMouse() {
        minnieMouse = Kontakt.createNewKontakt(new Navn("Minnie", "Mouse"));
    }

    private static Adresse opprettOgLeggTilAdresse(Kontakt kontakt, AdresseType type, String gatenavn, String gatenummer, String postnummer, String by, String landskode) {
        Adresse adresse = kontakt.opprettAdresse(type, gatenavn, gatenummer, postnummer, by, landskode);
        kontakt.leggTilAdresse(adresse);
        return adresse;
    }

    private static Epost opprettOgLeggTilEpostadresse(Kontakt kontakt, String epostadresse) {
        Epost epost = kontakt.opprettEpostadresse(epostadresse);
        kontakt.leggTilEpostadresse(epost);
        return epost;
    }

    private static Telefon opprettOgLeggTilTelefonnummer(Kontakt kontakt, TelefonType type, String landkode, String nummer) {
        Telefon telefon = kontakt.opprettTelefonnummer(type, landkode, nummer);
        kontakt.leggTilTelefonnummer(telefon);
        return telefon;
    }

    @Test
    void testOpprettOgLeggTilAdresse() {
        Adresse adresse = opprettOgLeggTilAdresse(minnieMouse, AdresseType.JOBB, "South Buena Vista Street", "500", "91521", "Burbank", "US");
        assertEquals(AdresseType.JOBB, adresse.type());
    }

    @Test
    void testOpprettOgLeggTilAdresseEnForMange() {
        assertThrowsExactly(DomeneException.class, () -> {
            opprettOgLeggTilAdresse(mickeyMouse, AdresseType.JOBB, "Some other", "15", "90201", "Beverley Hills", "US");
        });
    }

    @Test
    void testOpprettOgLeggTilAdresseDuplikat() {
        Adresse adresse1 = opprettOgLeggTilAdresse(minnieMouse, AdresseType.JOBB, "South Buena Vista Street", "500", "91521", "Burbank", "US");
        Adresse adresse2 = opprettOgLeggTilAdresse(minnieMouse, AdresseType.JOBB, "South Buena Vista Street", "500", "91521", "Burbank", "US");
        assertEquals(adresse1, adresse2);
        assertEquals(1, minnieMouse.hentAdresser().size()); // Det skal kun være en adresse
    }

    @Test
    void testSlettAdresse() {
        Adresse adresse1 = opprettOgLeggTilAdresse(minnieMouse, AdresseType.JOBB, "South Buena Vista Street", "500", "91521", "Burbank", "US");
        Adresse adresse2 = opprettOgLeggTilAdresse(minnieMouse, AdresseType.PRIVAT, "P.O. Box 10040", "", "32830-0040", "Lake Buena Vista", "US");
        assertEquals(2, minnieMouse.hentAdresser().size());

        minnieMouse.slettAdresse(adresse1);
        assertEquals(1, minnieMouse.hentAdresser().size());

        Adresse adresseFraKontakt = minnieMouse.hentAdresser().get(0);
        assertEquals(adresse2, adresseFraKontakt);
    }

    @Test
    void testOpprettOgLeggTilEpost() {
        Epost epost = opprettOgLeggTilEpostadresse(minnieMouse, "minnie.mouse@disney.com");
        assertEquals("minnie.mouse@disney.com", epost.epostadresse());
    }

    @Test
    void testSlettEpost() {
        Epost epost1 = opprettOgLeggTilEpostadresse(minnieMouse, "minnie.mouse@disney.com");
        Epost epost2 = opprettOgLeggTilEpostadresse(minnieMouse, "minnie@yahoo.com");
        Epost epost3 = opprettOgLeggTilEpostadresse(minnieMouse, "minnie@aol.com");
        assertEquals(3, minnieMouse.hentEpostadresser().size());

        minnieMouse.slettEpostadresse(epost3);
        minnieMouse.slettEpostadresse(epost2);
        assertEquals(1, minnieMouse.hentEpostadresser().size());

        Epost epostFraKontakt = minnieMouse.hentEpostadresser().get(0);
        assertEquals(epost1, epostFraKontakt);
    }

    @Test
    void testOpprettOgLeggTilTelefon() {
        Telefon telefon = opprettOgLeggTilTelefonnummer(minnieMouse, TelefonType.PRIVAT, "1", "(203) 555-9876");
        assertEquals(TelefonType.PRIVAT, telefon.type());
        assertEquals("1", telefon.landskode());
        assertEquals("(203) 555-9876", telefon.nummer());
        assertEquals(new Telefon(TelefonType.PRIVAT, "1", "(203) 555-9876"), telefon);
    }

    @Test
    void testSlettTelefon() {
        Telefon telefon1 = opprettOgLeggTilTelefonnummer(minnieMouse, TelefonType.PRIVAT, "1", "(203) 555-9876");
        Telefon telefon2 = opprettOgLeggTilTelefonnummer(minnieMouse, TelefonType.JOBB, "1", "(307) 555-1234");
        Telefon telefon3 = opprettOgLeggTilTelefonnummer(minnieMouse, TelefonType.ANNET, "47", "940 54 123");
        assertEquals(3, minnieMouse.hentTelefonnummerer().size());

        minnieMouse.slettTelefon(telefon3);
        minnieMouse.slettTelefon(telefon1);
        assertEquals(1, minnieMouse.hentTelefonnummerer().size());

        Telefon telefonFraKontakt = minnieMouse.hentTelefonnummerer().get(0);
        assertEquals(telefon2, telefonFraKontakt);
    }
}
