package rip.athena.AthenaSleeper.encryption;

import lombok.experimental.UtilityClass;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@UtilityClass
public class EncryptUUID {
    public String encrypt(UUID uuid) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
