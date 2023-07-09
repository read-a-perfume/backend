package encryptor.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AESEncryptorTest {

  private final AESEncryptor encryptor = new AESEncryptor("0123456789abcdef");

  @Test
  @DisplayName("AESEncryptor should encrypt the given text")
  void encrypt() throws Exception {
    // given
    String plainText = "plainText";
    String encryptedText = "FUm0wd5fOvhUF/YhR6cgzg==";

    // when
    String result = encryptor.encrypt(plainText);

    // then
    assertEquals(encryptedText, result);
  }

  @Test
  @DisplayName("AESEncryptor should decrypt the given text")
  void decrypt() throws Exception {
    // given
    String plainText = "plainText";
    String encryptedText = "FUm0wd5fOvhUF/YhR6cgzg==";

    // when
    String result = encryptor.decrypt(encryptedText);

    // then
    assertEquals(plainText, result);
  }
}
