package io.perfume.api.common.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.perfume.api.auth.application.port.in.MakeNewTokenUseCase;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

@Component
public class SignInAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final MakeNewTokenUseCase makeNewTokenUseCase;
  private final ObjectMapper objectMapper;

  public SignInAuthenticationFilter(AuthenticationManager authenticationManager,
                                    MakeNewTokenUseCase makeNewTokenUseCase) {
    this.objectMapper = new ObjectMapper();
    this.makeNewTokenUseCase = makeNewTokenUseCase;

    super.setAuthenticationManager(authenticationManager);
    super.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/v1/login", "POST"));
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
                                              HttpServletResponse response)
      throws AuthenticationException {
    SignInDto signInDto;
    try {
      signInDto = objectMapper.readValue(StreamUtils.copyToString(
          request.getInputStream(), StandardCharsets.UTF_8), SignInDto.class);
    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }

    UsernamePasswordAuthenticationToken authenticationToken
        = new UsernamePasswordAuthenticationToken(signInDto.getUsername(), signInDto.getPassword());
    return this.getAuthenticationManager().authenticate(authenticationToken);
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request,
                                          HttpServletResponse response,
                                          FilterChain filter,
                                          Authentication authResult) {
    UserPrincipal principal = (UserPrincipal) authResult.getPrincipal();

    String accessToken =
        makeNewTokenUseCase.createAccessToken(principal.getUser().getId(), LocalDateTime.now());
    response.addCookie(createCookie("X-Access-Token", accessToken));

    String refreshToken =
        makeNewTokenUseCase.createRefreshToken(principal.getUser().getId(), LocalDateTime.now());
    response.addCookie(createCookie("X-Refresh-Token", refreshToken));

    SecurityContextHolder.getContext().setAuthentication(authResult);
    try {
      response.getWriter().write(
          objectMapper.writeValueAsString(
              (authResult.getPrincipal())));
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
  }

  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            AuthenticationException failed) throws IOException {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);

    String errorMessage = "Authentication failed: " + failed.getMessage();
    response.getWriter().write(
        objectMapper.writeValueAsString(errorMessage));
  }

  private Cookie createCookie(String cookieName, String cookieValue) {
    Cookie cookie = new Cookie(cookieName, cookieValue);
    cookie.setSecure(true);
    cookie.setHttpOnly(true);

    return cookie;
  }
}
