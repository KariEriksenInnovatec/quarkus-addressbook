package net.innovatec.adressebok.api.exception;

public abstract class AdressebokException extends RuntimeException{
    public AdressebokException(String message) {
        super(message);
    }

    public AdressebokException(String message, Throwable cause) {
        super(message, cause);
    }    
}
