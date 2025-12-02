package net.innovatec.adressebok.infrastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;
import net.innovatec.adressebok.domain.AdressebokRepo;
import net.innovatec.adressebok.domain.model.Adressebok;
import net.innovatec.adressebok.domain.model.AdressebokId;

@ApplicationScoped
public class AdressebokRepoImpl implements AdressebokRepo {

    private List<Adressebok> adresseboker = new ArrayList<>();

    @Override
    public List<Adressebok> hentAlleAdresseboker() {
        return new ArrayList<>(adresseboker);
    }

    @Override
    public Adressebok opprettAdressebok() {

        // Generer en ny ID
        // Repo eier ID; unngår dupliketer, skal lagrets i database senere
        AdressebokId id = new AdressebokId(UUID.randomUUID());

        // Opprett og lagre en ny adressebok
        Adressebok adressebok = new Adressebok(id);
        adresseboker.add(adressebok);

        return adressebok;
    }

    @Override
    public Adressebok hentAdressebok(String adressebokId) {

        // Parse string-ID til UUID
        UUID uuid = UUID.fromString(adressebokId);

        // Søk gjennom alle adressebøker
        // Matche mot UUID, ikke String: raskere, tryggere, validering
        for (Adressebok adressebok : adresseboker) {
            if (adressebok.hentId().adressebokId().equals(uuid)) {
                return adressebok;
            }
        }
        // Hvis ikke funnet, kast exception
        throw new RuntimeException("Adressebok med id " + adressebokId + " ble ikke funnet");
    }

    @Override
    public void slettAdressebok(String adressebokId) {
        // Parse string-ID til UUID
        UUID uuid = UUID.fromString(adressebokId);

        // Søk gjennom alle adressebøker og fjern den med matching UUID
        for (int i = 0; i < adresseboker.size(); i++) {
            if (adresseboker.get(i).hentId().adressebokId().equals(uuid)) {
                adresseboker.remove(i);
                return;
            }
        }
        // Hvis ikke funnet, kast exception
        throw new RuntimeException("Adressebok med id " + adressebokId + " ble ikke funnet");
    }
}
