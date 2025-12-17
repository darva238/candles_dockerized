package service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordService {
    public String generateSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public String hashPassword(String password, String salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(Base64.getDecoder().decode(salt));
        byte[] hash = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }

    public boolean verifyPassword(String password, String salt, String hashedPassword) throws NoSuchAlgorithmException {
        String hashed = hashPassword(password, salt);
        return hashed.equals(hashedPassword);
    }
}
