package encryptor.impl;

import encryptor.OneWayEncryptor;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHAEncryptor implements OneWayEncryptor {

  @Override
  public String hash(String plainText) {
    try {
      java.security.MessageDigest md = MessageDigest.getInstance("SHA-256");
      byte[] array = md.digest(plainText.getBytes(StandardCharsets.UTF_8));
      StringBuilder sb = new StringBuilder();
      for (byte b : array) {
        sb.append(Integer.toHexString((b & 0xFF) | 0x100), 1, 3);
      }
      return sb.toString();
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }
}
