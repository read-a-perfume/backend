package io.perfume.api.common.jwt;

import org.springframework.security.core.AuthenticationException;

public class TokenExpiredException extends AuthenticationException {
  public TokenExpiredException() {
    super("Access Token Expired.");
  }
}
