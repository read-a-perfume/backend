package io.perfume.api.common.oauth2;

import generator.Generator;
import io.perfume.api.common.property.JsonWebTokenProperties;
import io.perfume.api.user.application.port.in.CreateUserUseCase;
import io.perfume.api.user.application.port.in.FindUserUseCase;
import io.perfume.api.user.application.port.in.dto.SignUpGeneralUserCommand;
import io.perfume.api.user.application.port.in.dto.UserResult;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jwt.JsonWebTokenGenerator;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends AbstractAuthenticationTargetUrlRequestHandler implements AuthenticationSuccessHandler {

    private final JsonWebTokenGenerator jsonWebTokenGenerator;

    private final JsonWebTokenProperties jsonWebTokenProperties;

    private final FindUserUseCase findUserUseCase;

    private final CreateUserUseCase createUserUseCase;

    private final Generator generator;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, @NotNull Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        UserResult userResult = newUserIfNotExists(oAuth2User);

        setResponseToken(response, userResult);

        String targetUrl = getRedirectUri();
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    private void setResponseToken(HttpServletResponse response, UserResult userResult) {
        String accessToken = createAccessToken(userResult.email());
        response.setHeader("Authorization", accessToken);

        String refreshToken = createRefreshToken(accessToken);
        Cookie cookie = new Cookie("X-Refresh-Token", refreshToken);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    @NotNull
    private String getRedirectUri() {
        return UriComponentsBuilder.fromHttpUrl(jsonWebTokenProperties.redirectUri())
                .build()
                .toUriString();
    }

    @NotNull
    private String createAccessToken(String email) {
        return jsonWebTokenGenerator.create(
                email,
                Map.of("roles", List.of("ROLE_USER")),
                jsonWebTokenProperties.accessTokenValidityInSeconds(),
                LocalDateTime.now()
        );
    }

    @NotNull
    private String createRefreshToken(String pairAccessToken) {
        return jsonWebTokenGenerator.create(
                pairAccessToken,
                Map.of(),
                jsonWebTokenProperties.refreshTokenValidityInSeconds(),
                LocalDateTime.now()
        );
    }

    private UserResult newUserIfNotExists(@NotNull OAuth2User oAuth2User) {
        String email = oAuth2User.getAttributes().get("email").toString();
        if (email == null) {
            throw new RuntimeException("Email not found from OAuth2 provider");
        }

        String name = oAuth2User.getAttributes().get("name").toString();
        if (name == null) {
            throw new RuntimeException("Name not found from OAuth2 provider");
        }

        return findUserUseCase.findOneByEmail(email).orElseGet(() -> {
            SignUpGeneralUserCommand command = new SignUpGeneralUserCommand(
                    createUsername(email, unixTime()),
                    generator.generate(30),
                    email,
                    false,
                    false,
                    name
            );
            return createUserUseCase.signUpGeneralUserByEmail(command);
        });
    }

    @NotNull
    private Long unixTime() {
        return Instant.now().getEpochSecond();
    }

    @NotNull
    private String createUsername(String email, Long unixTime) {
        if (Objects.isNull(email) || !email.contains("@")) {
            throw new IllegalArgumentException(email + " 올바른 이메일 형식이 아닙니다.");
        }

        return email.split("@")[0] + unixTime;
    }
}
