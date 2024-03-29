package io.perfume.api.common.jwt;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

  private final Object principal;
  private final Object credentials;

  public JwtAuthenticationToken(Object token) {
    super(null);
    this.principal = null;
    this.credentials = token;
    super.setAuthenticated(false);
  }

  public JwtAuthenticationToken(
      Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
    this.principal = principal;
    this.credentials = credentials;
    super.setAuthenticated(true);
  }

  static JwtAuthenticationToken authorized(
      Object principal, Collection<? extends GrantedAuthority> authorities) {
    return new JwtAuthenticationToken(principal, "", authorities);
  }

  @Override
  public Object getCredentials() {
    return credentials;
  }

  @Override
  public Object getPrincipal() {
    return principal;
  }
}
