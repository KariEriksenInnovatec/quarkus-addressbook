package net.innovatec.adressebok.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class AdressebokTest {
	private static Adressebok adressebok = null;
	private static KontaktId id = null;

	@BeforeAll
	public static void buildAdressebok() {
		adressebok = new Adressebok();
		opprettOgLeggTilKontakt(adressebok, "Mickey", "Mouse");
		opprettOgLeggTilKontakt(adressebok, "Minnie", "Mouse");
		opprettOgLeggTilKontakt(adressebok, "Daisy", "Duck");
		opprettOgLeggTilKontakt(adressebok, "Donald", "Duck");
		Kontakt kontakt = opprettOgLeggTilKontakt(adressebok, "Daddy", "Longlegs");
		id = kontakt.hentId();
		opprettOgLeggTilKontakt(adressebok, "Tom", "Enigma");
		
	}
	
	private static Kontakt opprettOgLeggTilKontakt(Adressebok adressebok, String fornavn, String etternavn) {
        Kontakt kontakt = new Kontakt(new Navn(fornavn, etternavn));
        adressebok.leggTilKontakt(kontakt);
        return kontakt;
	}

	@Test
	void testOpprettKontakt() {
		Kontakt kontakt = opprettOgLeggTilKontakt(adressebok, "Bugs", "Bunny");
		KontaktId id = kontakt.hentId();

		Kontakt hentetKontakt = adressebok.henteKontakt(id);
		assertEquals(kontakt, hentetKontakt);
	}

	@Test
	void testOpprettKontaktDerNavnAlleredeFinnesPåEnAnnenKontakt() {
		assertThrowsExactly(DomeneException.class, () -> {
			opprettOgLeggTilKontakt(adressebok, "Mickey", "Mouse");
		});
	}	

	@Test
	void testOpprettKontaktMedNullVerdiPåFornavn() {
		assertThrowsExactly(DomeneException.class, () -> {
			opprettOgLeggTilKontakt(adressebok, null, "Mouse");
		});
	}	

	@Test
	void testOpprettKontaktMedTomVerdiPåFornavn() {
		assertThrowsExactly(DomeneException.class, () -> {
			opprettOgLeggTilKontakt(adressebok, "", "Mouse");
		});
	}	
	@Test
	void testOpprettKontaktMedTomVerdiPåEtternavn() {
		assertThrowsExactly(DomeneException.class, () -> {
			opprettOgLeggTilKontakt(adressebok, "Mickey", "");
		});
	}	
	
	@Test
	void testOpprettKontaktMedForLangFornavn() {
		assertThrowsExactly(DomeneException.class, () -> {
			opprettOgLeggTilKontakt(adressebok, "Mickey har alt for lang fornavn for å kunne lagre i domene modellen men dette var vanseklig å få til over 100 tegn...", "Mouse");
		});
	}	
	
	@Test
	void testOpprettKontaktMedForLangEtternavn() {
		assertThrowsExactly(DomeneException.class, () -> {
			opprettOgLeggTilKontakt(adressebok, "Mickey", "Mouse kan ikke vare like lang som fornavn så dette bør være lettere...");
		});
	}	

	@Test
	void testHenteKontakt() {
		Kontakt hentetKontakt = adressebok.henteKontakt(id);
		assertEquals(new Navn("Daddy", "Longlegs"), hentetKontakt.hentNavn());
	}

	@Test
	void testSlettKontakt() {
		List<Kontakt> kontakter = adressebok.søkKontakt("Tom Enigma"); 
		assertEquals(1, kontakter.size());
		
		Boolean flag = adressebok.slettKontakt(kontakter.getFirst().hentId());
		assertEquals(flag, true);

		assertEquals(0, adressebok.søkKontakt("Tom Enigma").size());
	}

	@Test

	void testSlettKontaktSomIkkeFinnes() {
		List<Kontakt> kontakter = adressebok.søkKontakt("Harry Potter"); 
		assertEquals(0, kontakter.size());
		
		Boolean flag = adressebok.slettKontakt(new Kontakt(new Navn("Harry", "Potter")).hentId());
		assertEquals(flag, false);
	}

	@Test
	void testSøkKontaktPositive1() {
		List<Kontakt> result = adressebok.søkKontakt(".*Mouse");
		assertEquals(2, result.size());
	}

	@Test
	void testSøkKontaktPositive2() {
		List<Kontakt> result = adressebok.søkKontakt("^D.*s$");
		assertEquals(1, result.size());
	}

	@Test
	void testSøkFornavn() {
		List<Kontakt> result = adressebok.søkFornavn("Daisy");
		assertEquals(1, result.size());		
	}

	@Test
	void testSøkEtternavn() {
		List<Kontakt> result = adressebok.søkEtternavn("Mouse");
		assertEquals(2, result.size());		
	}

}
