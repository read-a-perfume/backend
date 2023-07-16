package io.perfume.api.common.oauth2;

import generator.Generator;
import io.perfume.api.auth.application.port.in.MakeNewTokenUseCase;
import io.perfume.api.common.jwt.JwtProperties;
import io.perfume.api.user.application.port.in.CreateUserUseCase;
import io.perfume.api.user.application.port.in.FindUserUseCase;
import io.perfume.api.user.application.port.in.dto.SignUpSocialUserCommand;
import io.perfume.api.user.application.port.in.dto.UserResult;
import io.perfume.api.user.domain.SocialProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class OAuth2SuccessHandler extends AbstractAuthenticationTargetUrlRequestHandler
    implements AuthenticationSuccessHandler {

  private final JwtProperties jwtProperties;

  private final FindUserUseCase findUserUseCase;

  private final CreateUserUseCase createUserUseCase;

  private final MakeNewTokenUseCase makeNewTokenUseCase;

  private final Generator generator;

  public OAuth2SuccessHandler(JwtProperties jwtProperties, FindUserUseCase findUserUseCase,
                              CreateUserUseCase createUserUseCase,
                              MakeNewTokenUseCase makeNewTokenUseCase, Generator generator) {
    this.jwtProperties = jwtProperties;
    this.findUserUseCase = findUserUseCase;
    this.createUserUseCase = createUserUseCase;
    this.makeNewTokenUseCase = makeNewTokenUseCase;
    this.generator = generator;
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      @NotNull Authentication authentication) throws IOException {
    OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
    LocalDateTime now = LocalDateTime.now();
    UserResult userResult = newUserIfNotExists(oauth2User, now);

    setResponseToken(response, userResult, now);

    String targetUrl = getRedirectUri();
    getRedirectStrategy().sendRedirect(request, response, targetUrl);
  }

  private void setResponseToken(HttpServletResponse response, UserResult userResult,
                                LocalDateTime now) {
    String accessToken = makeNewTokenUseCase.createAccessToken(userResult.id(), now);
    response.addCookie(createCookie("X-Access-Token", accessToken));

    String refreshToken = makeNewTokenUseCase.createRefreshToken(userResult.id(), now);
    response.addCookie(createCookie("X-Refresh-Token", refreshToken));
  }

  @NotNull
  private String getRedirectUri() {
    return UriComponentsBuilder.fromHttpUrl(jwtProperties.redirectUri())
        .build()
        .toUriString();
  }

  private UserResult newUserIfNotExists(@NotNull OAuth2User oauth2User, LocalDateTime now) {
    String email = oauth2User.getAttributes().get("email").toString();
    if (Objects.isNull(email)) {
      throw new RuntimeException("Email not found from OAuth2 provider");
    }

    String name = oauth2User.getAttributes().get("name").toString();
    if (Objects.isNull(name)) {
      throw new RuntimeException("Name not found from OAuth2 provider");
    }

    String identifier = oauth2User.getAttributes().get("sub").toString();
    if (Objects.isNull(identifier)) {
      throw new RuntimeException("Identifier not found from OAuth2 provider");
    }

    return findUserUseCase.findOneBySocialId(email).orElseGet(() -> {
      String randomPassword = generator.generate(30);
      SignUpSocialUserCommand command = new SignUpSocialUserCommand(
          identifier,
          generateRandomUsername(email, getUnixTime()),
          randomPassword,
          name,
          SocialProvider.GOOGLE
      );
      return createUserUseCase.signUpSocialUser(command, now);
    });
  }

  @NotNull
  private Long getUnixTime() {
    return Instant.now().getEpochSecond();
  }

  @NotNull
  private String generateRandomUsername(String email, Long unixTime) {
    if (Objects.isNull(email) || !email.contains("@")) {
      throw new IllegalArgumentException(email + " 올바른 이메일 형식이 아닙니다.");
    }

    return email.split("@")[0] + unixTime;
  }

  private Cookie createCookie(String cookieName, String cookieValue) {
    Cookie cookie = new Cookie(cookieName, cookieValue);
    cookie.setSecure(true);
    cookie.setHttpOnly(true);

    return cookie;
  }
}
