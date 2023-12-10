package io.perfume.api.common.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.perfume.api.common.advice.ErrorResponse;
import io.perfume.api.common.auth.Constants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final AuthenticationManager authenticationManager;
  private final ObjectMapper objectMapper;

  @Override
  protected void doFilterInternal(
      @NotNull HttpServletRequest request,
      @NotNull HttpServletResponse response,
      @NotNull FilterChain filterChain)
      throws ServletException, IOException {
    var cookies = request.getCookies();

    if (!Objects.isNull(cookies)) {
      Arrays.stream(cookies)
          .filter(cookie -> cookie.getName().equalsIgnoreCase(Constants.ACCESS_TOKEN_KEY))
          .findFirst()
          .map(Cookie::getValue)
          .ifPresent(
              value -> {
                try {
                  var authentication =
                      authenticationManager.authenticate(new JwtAuthenticationToken(value));
                  SecurityContextHolder.getContext().setAuthentication(authentication);
                } catch (AuthenticationException e) {
                  unsuccessfulAuthentication(response, e);
                }
              });
      if (SecurityContextHolder.getContext().getAuthentication() != null) {
        filterChain.doFilter(request, response);
      }
    } else {
      filterChain.doFilter(request, response);
    }
  }

  private void unsuccessfulAuthentication(
      @NotNull HttpServletResponse response, AuthenticationException ae) {
    try {
      ErrorResponse errorResponse =
          ErrorResponse.builder()
              .statusCode(HttpStatus.UNAUTHORIZED.value())
              .status(HttpStatus.UNAUTHORIZED.getReasonPhrase())
              .message(ae.getMessage())
              .build();
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
  }
}
