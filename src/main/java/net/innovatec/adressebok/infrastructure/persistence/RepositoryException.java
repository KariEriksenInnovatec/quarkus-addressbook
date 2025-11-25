package net.innovatec.adressebok.infrastructure.persistence;

public class RepositoryException extends RuntimeException {
    private static final long serialVersionUID = 2817051093939747881L;

    public RepositoryException(String melding) {
        super(melding);
    }   

}
