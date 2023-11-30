package io.perfume.api.common.jwt;

import org.springframework.security.core.AuthenticationException;

public class TokenNotFoundException extends AuthenticationException {
  public TokenNotFoundException() {
    super("Token doesn't exist. Please Login.");
  }
}
