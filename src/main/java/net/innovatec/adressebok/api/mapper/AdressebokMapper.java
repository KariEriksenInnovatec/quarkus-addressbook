package net.innovatec.adressebok.api.mapper;

import java.util.List;
import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import net.innovatec.adressebok.beans.AdressebokMedKontakterResponse;
import net.innovatec.adressebok.beans.AdressebokResponse;
import net.innovatec.adressebok.beans.KontaktResponse;
import net.innovatec.adressebok.domain.model.Adressebok;
import net.innovatec.adressebok.domain.model.Kontakt;

// Lag en implementasjon av dette interface-et
// Registrer den som en CDI-bean
@Mapper(componentModel = "cdi")
public interface AdressebokMapper {

    // Mapping av Adressebok (fra domene til api)

    @Mapping(source = ".", target = "id", qualifiedByName = "adressebokIdTilUuid")
    @Mapping(source = ".", target = "antallKontakter", qualifiedByName = "kontakterTilAntall")

    AdressebokResponse toResponse(Adressebok adressebok);

    List<AdressebokResponse> toResponse(List<Adressebok> adresseboker);

    @Named("adressebokIdTilUuid")
    default UUID adressebokIdTilUuid(Adressebok adressebok) {
        return adressebok != null && adressebok.hentId() != null ? adressebok.hentId().adressebokId() : null;
    }

    @Named("kontakterTilAntall")
    default Integer kontakterTilAntall(Adressebok adressebok) {
        List<Kontakt> kontakter = adressebok != null ? adressebok.hentKontakter() : null;
        return kontakter != null ? kontakter.size() : 0;
    }

    // Mapping av Kontakt (fra domene til api)

    @Mapping(source = ".", target = "id", qualifiedByName = "kontaktIdTilUuid")
    @Mapping(source = "navn", target = "navn")
    @Mapping(source = "adresser", target = "adresser")
    @Mapping(source = "epost", target = "epost")
    @Mapping(source = "telefon", target = "telefon")

    KontaktResponse toKontaktResponse(Kontakt kontakt);

    List<KontaktResponse> toKontaktResponse(List<Kontakt> kontakter);

    @Named("kontaktIdTilUuid")
    default UUID kontaktIdTilUuid(Kontakt kontakt) {
        return kontakt != null && kontakt.hentId() != null ? kontakt.hentId().kontaktId() : null;
    }

    @Mapping(source = ".", target = "id", qualifiedByName = "adressebokIdTilUuid")
    @Mapping(source = "kontakter", target = "kontakter")

    AdressebokMedKontakterResponse toAdressebokMedKontaktResponse(Adressebok adressebok);

    // Mapping av Adressebok (fra api til domene)

    // Mapping av Kontakt (fra api til domene)

}
