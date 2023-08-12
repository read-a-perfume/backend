package io.perfume.api.common.jwt;

import io.perfume.api.common.auth.Constants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
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
    var cookies = request.getCookies();
    if (!Objects.isNull(cookies)) {
      Arrays.stream(cookies)
          .filter(cookie -> cookie.getName().equalsIgnoreCase(Constants.ACCESS_TOKEN_KEY))
          .findFirst()
          .map(Cookie::getValue)
          .ifPresent(value -> {
            var authentication =
                authenticationManager.authenticate(new JwtAuthenticationToken(value));
            SecurityContextHolder.getContext().setAuthentication(authentication);
          });
    }

    filterChain.doFilter(request, response);
  }
}
