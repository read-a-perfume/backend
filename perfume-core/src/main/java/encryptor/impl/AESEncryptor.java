package encryptor.impl;

import encryptor.TwoWayEncryptor;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESEncryptor implements TwoWayEncryptor {

    private static final String ALGORITHM = "AES";

    private final String secretKey;

    public AESEncryptor(String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public String encrypt(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(secretKey.getBytes(), ALGORITHM));
        return Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes()));
    }

    @Override
    public String decrypt(String encryptedText) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(secretKey.getBytes(), ALGORITHM));
        return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedText)));
    }
}
