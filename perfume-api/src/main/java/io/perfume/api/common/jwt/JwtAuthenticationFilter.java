package io.perfume.api.common.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final AuthenticationManager authenticationManager;

  public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  protected void doFilterInternal(@NotNull HttpServletRequest request,
                                  @NotNull HttpServletResponse response,
                                  @NotNull FilterChain filterChain)
      throws ServletException, IOException {
    Arrays.stream(request.getCookies())
        .filter(cookie -> cookie.getName().equalsIgnoreCase("x-access-token"))
        .findFirst()
        .map(Cookie::getValue)
        .ifPresent(value -> {
          var authentication =
              authenticationManager.authenticate(new JwtAuthenticationToken(value));
          SecurityContextHolder.getContext().setAuthentication(authentication);
        });

    filterChain.doFilter(request, response);
  }
}
