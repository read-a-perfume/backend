package io.perfume.api.common.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import jwt.JsonWebTokenGenerator;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;


public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final AuthenticationManager authenticationManager;
  private final JsonWebTokenGenerator jsonWebTokenGenerator;

  public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
                                 JsonWebTokenGenerator jsonWebTokenGenerator) {
    this.authenticationManager = authenticationManager;
    this.jsonWebTokenGenerator = jsonWebTokenGenerator;
  }

  @Override
  protected void doFilterInternal(@NotNull HttpServletRequest request,
                                  @NotNull HttpServletResponse response,
                                  @NotNull FilterChain filterChain)
      throws ServletException, IOException {
    String jwt = jsonWebTokenGenerator.getTokenFromHeader(request);

    if (Objects.isNull(jwt)) {
      filterChain.doFilter(request, response);
      return;
    }

    Authentication authentication =
        authenticationManager.authenticate(new JwtAuthenticationToken(jwt));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    filterChain.doFilter(request, response);
  }
}
