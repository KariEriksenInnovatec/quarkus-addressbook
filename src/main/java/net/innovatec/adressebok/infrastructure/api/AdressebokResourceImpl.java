package net.innovatec.adressebok.infrastructure.api;

import org.jboss.logging.Logger;

import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import net.innovatec.adressebok.application.AdressebokService;
import net.innovatec.adressebok.infrastructure.api.beans.Adressebok;
import net.innovatec.adressebok.infrastructure.api.beans.AdressebokId;
import net.innovatec.adressebok.infrastructure.api.beans.Kontakt;
import net.innovatec.adressebok.infrastructure.api.beans.KontaktData;
import net.innovatec.adressebok.infrastructure.api.beans.KontaktId;
import net.innovatec.adressebok.infrastructure.mapper.AdressebokMapper;

public class AdressebokResourceImpl implements AdressebokResource {
    private final Logger log = Logger.getLogger(this.getClass());
    
    @Inject
    AdressebokService service;
    
    @Inject
    AdressebokMapper mapper;
    
    @Override
    public Adressebok hentAdressebok(@NotNull String adressebokId) {
        net.innovatec.adressebok.domain.model.AdressebokId id = mapper.stringToAdressebokId(adressebokId); 
        return mapper.toDto( service.hentAdressebok(id));        
    }

    @Override
    public AdressebokId opprettAdressebok() {
        return mapper.toDto( service.opprettAdressbok());
    }

    @Override
    public void slettAdressebok(@NotNull String adressebokId) {
        service.slettAdressebok(mapper.stringToAdressebokId(adressebokId));      
    }

    @Override
    public KontaktId opprettKontakt(@NotNull String adressebokId, @NotNull KontaktData dtoKontakt) {
      net.innovatec.adressebok.domain.model.Adressebok bok =  service.hentAdressebok(mapper.stringToAdressebokId(adressebokId));
      net.innovatec.adressebok.domain.model.KontaktData dmoKontakt = mapper.toDomain(dtoKontakt);     
      net.innovatec.adressebok.domain.model.KontaktId dmoKontaktId = service.opprettOgLeggTilKontakt(bok.getId(), dmoKontakt);
      KontaktId dtoKontaktId = new KontaktId();
      dtoKontaktId.setValue(dmoKontaktId.getId());
      return dtoKontaktId;
    }

    @Override
    public Kontakt hentKontakt(@NotNull String adressebokId, @NotNull String kontaktId) {        
        net.innovatec.adressebok.domain.model.Kontakt dmoKontakt = service.hentKontakt(mapper.stringToAdressebokId(adressebokId), mapper.stringToKontaktId(kontaktId));
        Kontakt dtoKontakt = mapper.toDto(dmoKontakt); 
        return dtoKontakt;
    }

    @Override
    public void oppdaterKontakt(@NotNull String adressebokId, @NotNull String kontaktId, @NotNull KontaktData data) {
        net.innovatec.adressebok.domain.model.Adressebok bok =  service.hentAdressebok(mapper.stringToAdressebokId(adressebokId));
        bok.oppdatereKontakt(mapper.stringToKontaktId(kontaktId), mapper.toDomain(data));
    }
    
    @Override
    public void slettKontakt(@NotNull String adressebokId, @NotNull String kontaktId) {
        service.slettKontakt(mapper.stringToAdressebokId(adressebokId), mapper.stringToKontaktId(kontaktId));
    }

        
}
