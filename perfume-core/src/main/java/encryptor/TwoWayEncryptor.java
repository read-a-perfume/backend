package encryptor;

public interface TwoWayEncryptor {

    String encrypt(String plainText) throws Exception;

    String decrypt(String encryptedText) throws Exception;
}
