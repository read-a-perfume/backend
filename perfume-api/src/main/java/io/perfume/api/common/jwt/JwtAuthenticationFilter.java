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
import java.util.Optional;
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
      @NotNull final HttpServletRequest request,
      @NotNull final HttpServletResponse response,
      @NotNull final FilterChain filterChain)
      throws ServletException, IOException {
    final Optional<String> cookies = findAuthenticationCookies(request);

    cookies.ifPresent(value -> tryToAuthenticate(response, new JwtAuthenticationToken(value)));

    filterChain.doFilter(request, response);
  }

  private Optional<String> findAuthenticationCookies(@NotNull HttpServletRequest request) {
    var cookies = request.getCookies();
    if (Objects.isNull(cookies)) {
      return Optional.empty();
    }

    return Arrays.stream(cookies)
        .filter(cookie -> cookie.getName().equalsIgnoreCase(Constants.ACCESS_TOKEN_KEY))
        .map(Cookie::getValue)
        .findFirst();
  }

  private void tryToAuthenticate(
      @NotNull HttpServletResponse response, JwtAuthenticationToken token) {
    try {
      var authentication = authenticationManager.authenticate(token);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    } catch (AuthenticationException e) {
      handleAuthenticationException(response, e);
    }
  }

  private void handleAuthenticationException(
      @NotNull HttpServletResponse response, AuthenticationException exception) {
    try {
      ErrorResponse errorResponse = createErrorResponse(exception);
      configureResponse(response, errorResponse);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
  }

  private ErrorResponse createErrorResponse(AuthenticationException exception) {
    return ErrorResponse.builder()
        .statusCode(HttpStatus.UNAUTHORIZED.value())
        .status(HttpStatus.UNAUTHORIZED.getReasonPhrase())
        .message(exception.getMessage())
        .build();
  }

  private void configureResponse(@NotNull HttpServletResponse response, ErrorResponse errorResponse)
      throws IOException {
    response.setStatus(errorResponse.getStatusCode());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
  }
}
