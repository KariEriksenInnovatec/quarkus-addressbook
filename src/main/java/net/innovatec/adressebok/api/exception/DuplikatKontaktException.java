package net.innovatec.adressebok.api.exception;

import net.innovatec.adressebok.domain.model.Navn;

public class DuplikatKontaktException extends AdressebokException {
    public DuplikatKontaktException(Navn navn) {
        super("En kontakt med navn " + navn.fornavn() + " " + navn.etternavn() + " eksisterer allerede");
    }
}