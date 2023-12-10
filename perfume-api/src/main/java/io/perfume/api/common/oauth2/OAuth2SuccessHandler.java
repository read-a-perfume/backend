package io.perfume.api.common.oauth2;

import generator.Generator;
import io.perfume.api.auth.application.port.in.MakeNewTokenUseCase;
import io.perfume.api.common.auth.Constants;
import io.perfume.api.common.jwt.JwtProperties;
import io.perfume.api.user.application.port.in.CreateUserUseCase;
import io.perfume.api.user.application.port.in.FindUserUseCase;
import io.perfume.api.user.application.port.in.dto.SignUpSocialUserCommand;
import io.perfume.api.user.application.port.in.dto.UserResult;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
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

  public OAuth2SuccessHandler(
      JwtProperties jwtProperties,
      FindUserUseCase findUserUseCase,
      CreateUserUseCase createUserUseCase,
      MakeNewTokenUseCase makeNewTokenUseCase,
      Generator generator) {
    this.jwtProperties = jwtProperties;
    this.findUserUseCase = findUserUseCase;
    this.createUserUseCase = createUserUseCase;
    this.makeNewTokenUseCase = makeNewTokenUseCase;
    this.generator = generator;
  }

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request,
      HttpServletResponse response,
      @NotNull Authentication authentication)
      throws IOException {
    OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
    LocalDateTime now = LocalDateTime.now();
    UserResult userResult = newUserIfNotExists(oauth2User, now);

    setResponseToken(response, userResult, now);

    String targetUrl = getRedirectUri();
    getRedirectStrategy().sendRedirect(request, response, targetUrl);
  }

  private void setResponseToken(
      HttpServletResponse response, UserResult userResult, LocalDateTime now) {
    String accessToken = makeNewTokenUseCase.createAccessToken(userResult.id(), now);
    response.addCookie(createCookie(Constants.ACCESS_TOKEN_KEY, accessToken));

    String refreshToken = makeNewTokenUseCase.createRefreshToken(userResult.id(), now);
    response.addCookie(createCookie(Constants.REFRESH_TOKEN_KEY, refreshToken));
  }

  @NotNull
  private String getRedirectUri() {
    return UriComponentsBuilder.fromHttpUrl(jwtProperties.redirectUri()).build().toUriString();
  }

  private UserResult newUserIfNotExists(@NotNull OAuth2User oauth2User, LocalDateTime now) {
    var attributes = oauth2User.getAttributes();
    String identifier = String.valueOf(getAttribute(attributes, "sub"));

    return findUserUseCase
        .findOneBySocialId(identifier)
        .orElseGet(
            () -> {
              String randomPassword = generator.generate(30);
              String email = String.valueOf(getAttribute(attributes, "email"));
              String name = String.valueOf(getAttribute(attributes, "name"));
              SignUpSocialUserCommand command =
                  SignUpSocialUserCommand.byGoogle(identifier, email, randomPassword, name);

              return createUserUseCase.signUpSocialUser(command, now);
            });
  }

  private Cookie createCookie(String cookieName, String cookieValue) {
    Cookie cookie = new Cookie(cookieName, cookieValue);
    cookie.setHttpOnly(true);

    return cookie;
  }

  private Object getAttribute(Map<String, Object> attributes, String key) {
    Object value = attributes.get(key);
    if (Objects.isNull(value)) {
      throw new RuntimeException(key + " not found from OAuth2 provider");
    }

    return value;
  }
}
