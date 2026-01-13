package net.innovatec.adressebok.api.exception;

public class UgyldigIdException extends UgyldigInputException {
    public UgyldigIdException(String id, String type) {
        super("Ugyldig " + type + " ID: " + id);
    }
    
    public UgyldigIdException(String id, String type, Throwable cause) {
        super("Ugyldig " + type + " ID: " + id, cause);
    }
}