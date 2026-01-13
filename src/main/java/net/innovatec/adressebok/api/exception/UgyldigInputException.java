package net.innovatec.adressebok.api.exception;

public abstract class UgyldigInputException extends AdressebokException {
    public UgyldigInputException(String message) {
        super(message);
    }
    
    public UgyldigInputException(String message, Throwable cause) {
        super(message, cause);
    }
}