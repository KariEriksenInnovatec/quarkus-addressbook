package net.innovatec.adressebok.api.exception;

import net.innovatec.adressebok.domain.model.KontaktId;

public class KontaktIkkeFunnetException extends IkkeFunnetException {
    public KontaktIkkeFunnetException(KontaktId id) {
        super("Kontakt med ID " + id + " ble ikke funnet");
    }
}