package net.innovatec.adressebok.api.exception;

public class MaksAntallOverskredetException extends UgyldigInputException {
    public MaksAntallOverskredetException(String entitet, int maks) {
        super("Maksimalt antall " + entitet + " (" + maks + ") er overskredet");
    }
}