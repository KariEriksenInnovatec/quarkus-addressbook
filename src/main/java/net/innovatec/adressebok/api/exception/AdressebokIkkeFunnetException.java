package net.innovatec.adressebok.api.exception;

import net.innovatec.adressebok.domain.model.AdressebokId;

public class AdressebokIkkeFunnetException extends IkkeFunnetException {
    public AdressebokIkkeFunnetException(AdressebokId id) {
        super("Adressebok med ID " + id.adressebokId() + " ble ikke funnet");
    }
}