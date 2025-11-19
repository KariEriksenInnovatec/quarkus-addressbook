package net.innovatec.adressebok.infrastructure.mapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import net.innovatec.adressebok.domain.model.Adresse;
import net.innovatec.adressebok.domain.model.Adressebok;
import net.innovatec.adressebok.domain.model.AdressebokId;
import net.innovatec.adressebok.domain.model.Epost;
import net.innovatec.adressebok.domain.model.Kontakt;
import net.innovatec.adressebok.domain.model.KontaktData;
import net.innovatec.adressebok.domain.model.KontaktId;
import net.innovatec.adressebok.domain.model.Navn;
import net.innovatec.adressebok.domain.model.Telefon;

@Mapper(componentModel = "cdi")
public interface AdressebokMapper {
    // Mapping for Adressebok    
    default Adressebok toDomain(net.innovatec.adressebok.infrastructure.api.beans.Adressebok dto) {
        AdressebokId bokid = toDomain(dto.getId());
        Adressebok bok = new Adressebok(bokid);
        for(net.innovatec.adressebok.infrastructure.api.beans.Kontakt dtoElement: dto.getKontakter()) {
            bok.leggTilKontakt(toDomain(dtoElement));
        }
        return bok; 
    }

    default AdressebokId toDomain(net.innovatec.adressebok.infrastructure.api.beans.AdressebokId dto) {
        return dto != null ? new AdressebokId(dto.getValue()) : null;
    }
    
    default AdressebokId stringToAdressebokId(String id) {
        return id != null ? new AdressebokId(UUID.fromString(id)) : null;
    }
        
    default net.innovatec.adressebok.infrastructure.api.beans.Adressebok toDto(Adressebok adressebok) {
        net.innovatec.adressebok.infrastructure.api.beans.Adressebok dto = new net.innovatec.adressebok.infrastructure.api.beans.Adressebok();
        dto.setId(toDto(adressebok.getId())); 
        dto.setKontakter(mapKontakterToList(adressebok.hentKontakter()));
        return dto;        
    }

    @Mapping(source = "id", target = "value")
    net.innovatec.adressebok.infrastructure.api.beans.AdressebokId toDto(AdressebokId adressebokid);
    
    // Custom method to map Map<KontaktId, Kontakt> to List<KontaktDTO>
    default List<net.innovatec.adressebok.infrastructure.api.beans.Kontakt> mapKontakterToList(List<Kontakt> kontakter) {
        return kontakter.stream()
                .map(this::toDto) // Assuming you have a method to map Kontakt to KontaktDTO
                .collect(Collectors.toList());
    }
    
    // Mapping for Kontakt
    default Kontakt toDomain(net.innovatec.adressebok.infrastructure.api.beans.Kontakt dto) {
        KontaktId kontaktId = toDomain(dto.getId());
        Navn navn = toDomain(dto.getNavn());
        Kontakt kontakt = new Kontakt(kontaktId, navn);
        for (net.innovatec.adressebok.infrastructure.api.beans.Adresse dtoElement : dto.getAdresser()) {
            kontakt.leggTilAdresse(toDomain(dtoElement));
        }
        for (net.innovatec.adressebok.infrastructure.api.beans.Epost dtoElement : dto.getEpostadresser()) {
            kontakt.leggTilEpostadresse(toDomain(dtoElement));
        }
        for (net.innovatec.adressebok.infrastructure.api.beans.Telefon dtoElement : dto.getTelefonnumre()) {
            kontakt.leggTilTelefonnummer(toDomain(dtoElement));
        }
        return kontakt;
    }
        
    default net.innovatec.adressebok.infrastructure.api.beans.Kontakt toDto(Kontakt kontakt) {
        net.innovatec.adressebok.infrastructure.api.beans.Kontakt dto = new net.innovatec.adressebok.infrastructure.api.beans.Kontakt();
        net.innovatec.adressebok.infrastructure.api.beans.KontaktId dtoId = toDto(kontakt.hentId());
        dto.setId(dtoId);
        dto.setNavn(toDto(kontakt.hentNavn()));
        for (Adresse element : kontakt.hentAdresser()) {
            dto.getAdresser().add(toDto(element));
        }
        for (Epost element : kontakt.hentEpostadresser()) {
            dto.getEpostadresser().add(toDto(element));
        }
        for (Telefon element : kontakt.hentTelefonnummerer()) {
            dto.getTelefonnumre().add(toDto(element));
        }
        return dto;
    }

    // Denne antar at hvis angitt kontaktid er null så skal den få en ny en.
    default KontaktId toDomain(net.innovatec.adressebok.infrastructure.api.beans.KontaktId dto) {
        return dto != null ? new KontaktId(dto.getValue()) : new KontaktId();
    }

    default KontaktId stringToKontaktId(String id) {
        return id != null ? new KontaktId(UUID.fromString(id)) : null;
    }
    
    default net.innovatec.adressebok.infrastructure.api.beans.KontaktId toDto(KontaktId kontaktid) {
        net.innovatec.adressebok.infrastructure.api.beans.KontaktId dtoId = new net.innovatec.adressebok.infrastructure.api.beans.KontaktId();
        dtoId.setValue(kontaktid.getId());
        return dtoId;
    }

    // Mapping between UUID and AdressebokId
    @Named("mapUUIDToKontaktId")
    default UUID mapAdressebokIdToUUID(KontaktId kontaktId) {
        return kontaktId != null ? kontaktId.getId() : null;
    }

    @Named("mapKontaktIdToUUID")
    default KontaktId mapUUIDToKontaktId(UUID id) {
        return id != null ? new KontaktId(id) : null;
    }

    // Mapping for KontaktData
    KontaktData toDomain(net.innovatec.adressebok.infrastructure.api.beans.KontaktData dto);
    
    net.innovatec.adressebok.infrastructure.api.beans.KontaktData toDto(KontaktData data);
    
    // Mapping for Navn
    Navn toDomain(net.innovatec.adressebok.infrastructure.api.beans.Navn dto);

    net.innovatec.adressebok.infrastructure.api.beans.Navn toDto(Navn navn);

    // Mapping for Adresse
    @Mapping(source = "adressetype", target = "type")
    Adresse toDomain(net.innovatec.adressebok.infrastructure.api.beans.Adresse dto);

    @Mapping(source = "type", target = "adressetype")
    net.innovatec.adressebok.infrastructure.api.beans.Adresse toDto(Adresse adresse);

    // Mapping for Epost
    Epost toDomain(net.innovatec.adressebok.infrastructure.api.beans.Epost dto);

    net.innovatec.adressebok.infrastructure.api.beans.Epost toDto(Epost epost);

    // Mapping for Telefon
    @Mapping(source = "telefontype", target = "type")
    @Mapping(source = "telefonnummer", target = "nummer")
    Telefon toDomain(net.innovatec.adressebok.infrastructure.api.beans.Telefon dto);

    @Mapping(source = "type", target = "telefontype")
    @Mapping(source = "nummer", target = "telefonnummer")
    net.innovatec.adressebok.infrastructure.api.beans.Telefon toDto(Telefon telefon);

}
