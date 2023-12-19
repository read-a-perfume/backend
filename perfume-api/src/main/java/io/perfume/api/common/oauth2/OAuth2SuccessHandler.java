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
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends AbstractAuthenticationTargetUrlRequestHandler
    implements AuthenticationSuccessHandler {

  private final JwtProperties jwtProperties;
  private final FindUserUseCase findUserUseCase;
  private final CreateUserUseCase createUserUseCase;
  private final MakeNewTokenUseCase makeNewTokenUseCase;
  private final Generator generator;

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request,
      HttpServletResponse response,
      @NotNull Authentication authentication)
      throws IOException {
    OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
    LocalDateTime now = LocalDateTime.now();
    UserResult userResult = handleOAuth2Success(oauth2User, now);

    addTokens(response, userResult, now);

    getRedirectStrategy().sendRedirect(request, response, generateSuccessRedirectUri());
  }

  private void addTokens(HttpServletResponse response, UserResult userResult, LocalDateTime now) {
    String accessToken = makeNewTokenUseCase.createAccessToken(userResult.id(), now);
    response.addCookie(generateCookie(Constants.ACCESS_TOKEN_KEY, accessToken));

    String refreshToken = makeNewTokenUseCase.createRefreshToken(userResult.id(), now);
    response.addCookie(generateCookie(Constants.REFRESH_TOKEN_KEY, refreshToken));
  }

  private String generateSuccessRedirectUri() {
    return UriComponentsBuilder.fromHttpUrl(jwtProperties.redirectUri()).build().toUriString();
  }

  private UserResult handleOAuth2Success(@NotNull OAuth2User oauth2User, LocalDateTime now) {
    final Map<String, Object> attributes = oauth2User.getAttributes();
    final String IDENTIFIER_ATTRIBUTE_KEY = "sub";
    final String identifier =
        String.valueOf(getValueFromAttributes(attributes, IDENTIFIER_ATTRIBUTE_KEY));

    return findUserUseCase
        .findOneBySocialId(identifier)
        .orElseGet(() -> createNewUser(attributes, identifier, now));
  }

  private UserResult createNewUser(
      Map<String, Object> attributes, String identifier, LocalDateTime now) {
    String randomPassword = generator.generate(30);
    String email = String.valueOf(getValueFromAttributes(attributes, "email"));
    String name = String.valueOf(getValueFromAttributes(attributes, "name"));

    SignUpSocialUserCommand command =
        SignUpSocialUserCommand.byGoogle(identifier, email, randomPassword, name, now);
    return createUserUseCase.createSocialUser(command);
  }

  private Cookie generateCookie(String cookieName, String cookieValue) {
    Cookie cookie = new Cookie(cookieName, cookieValue);
    cookie.setHttpOnly(true);
    return cookie;
  }

  private Object getValueFromAttributes(Map<String, Object> attributes, String key) {
    Object value = attributes.get(key);
    return Objects.requireNonNull(value);
  }
}
