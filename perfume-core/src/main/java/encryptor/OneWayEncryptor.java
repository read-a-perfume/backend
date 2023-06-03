package encryptor;

public interface OneWayEncryptor {

    String hash(String plainText);
}
