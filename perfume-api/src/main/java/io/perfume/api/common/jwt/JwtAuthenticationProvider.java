package io.perfume.api.common.jwt;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import jwt.JsonWebTokenGenerator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
@Qualifier("JwtAuthenticationProvider")
public class JwtAuthenticationProvider implements AuthenticationProvider {

  private final JsonWebTokenGenerator jsonWebTokenGenerator;

  public JwtAuthenticationProvider(JsonWebTokenGenerator jsonWebTokenGenerator) {
    this.jsonWebTokenGenerator = jsonWebTokenGenerator;
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String token = extractAuthenticateToken(authentication);
    verifyTokenExpiration(token);
    return processJwtAuthentication(token);
  }

  private void verifyTokenExpiration(String token) {
    LocalDateTime now = LocalDateTime.now();
    if (!jsonWebTokenGenerator.verify(token, now)) {
      throw new TokenExpiredException();
    }
  }

  private static String extractAuthenticateToken(Authentication authentication) {
    if (Objects.isNull(authentication)) {
      throw new IllegalArgumentException("Authentication cannot be null");
    }
    if (!(authentication instanceof JwtAuthenticationToken)) {
      throw new IllegalArgumentException(
          "Authentication is not JwtAuthenticationToken, but " + authentication.getClass());
    }
    return authentication.getCredentials().toString();
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return JwtAuthenticationToken.class.isAssignableFrom(authentication);
  }

  private Authentication processJwtAuthentication(String jwt) {
    final Long userId = getUserIdFromToken(jwt);
    List<? extends GrantedAuthority> roles = getAuthoritiesFromToken(jwt);

    return JwtAuthenticationToken.authorized(new User(userId.toString(), "", roles), roles);
  }

  private Long getUserIdFromToken(String jwt) {
    String USER_IDENTITY_CLAIM_NAME = "userId";
    return jsonWebTokenGenerator.getClaim(jwt, USER_IDENTITY_CLAIM_NAME, Long.class);
  }

  private List<SimpleGrantedAuthority> getAuthoritiesFromToken(String authenticationToken) {
    final String ROLES_CLAIM_NAME = "roles";
    final List<String> roles =
        jsonWebTokenGenerator.getClaim(authenticationToken, ROLES_CLAIM_NAME, List.class);
    return roles.stream().map(SimpleGrantedAuthority::new).toList();
  }
}
