package io.perfume.api.user.stub;

import org.springframework.security.crypto.password.PasswordEncoder;

public class StubEncryptor implements PasswordEncoder {
  @Override
  public String encode(CharSequence rawPassword) {
    return rawPassword.toString();
  }

  @Override
  public boolean matches(CharSequence rawPassword, String encodedPassword) {
    return rawPassword.toString().equals(encodedPassword);
  }
}
