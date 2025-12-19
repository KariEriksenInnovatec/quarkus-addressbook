package net.innovatec.adressebok.infrastructure.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import net.innovatec.adressebok.domain.model.Kontakt;

public class ChecksumUtil {

    public static String calculateChecksum(Kontakt kontakt) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        
        // Concatenate the fields of the Kontakt object
        StringBuilder data = new StringBuilder();
        data.append(kontakt.hentId().toString());
        data.append(kontakt.hentData().navn().fornavn());
        data.append(kontakt.hentData().navn().etternavn());
        
        // Add addresses
        for (var adresse : kontakt.hentData().adresser()) {
            data.append(adresse.type());
            data.append(adresse.gatenavn());
            data.append(adresse.gatenummer());
            data.append(adresse.postnummer());
            data.append(adresse.by());
            data.append(adresse.landkode());
        }
        
        // Add email addresses
        for (var epost : kontakt.hentData().epostadresser()) {
            data.append(epost.epostadresse());
        }
        
        // Add phone numbers
        for (var telefon : kontakt.hentData().telefonnumre()) {
            data.append(telefon.type());
            data.append(telefon.landskode());
            data.append(telefon.nummer());
        }
        
        // Calculate the checksum
        byte[] hash = digest.digest(data.toString().getBytes());
        
        // Convert the byte array to a hexadecimal string
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        
        return hexString.toString();
    }
}
