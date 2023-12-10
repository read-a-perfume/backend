package io.perfume.api.common.auth;

import org.springframework.security.core.AuthenticationException;

public class SignInFormInValidException extends AuthenticationException {

  public SignInFormInValidException(String msg) {
    super(msg);
  }
}
