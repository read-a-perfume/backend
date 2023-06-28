package io.perfume.api.common.oauth2;

import generator.Generator;
import io.perfume.api.auth.application.port.in.MakeNewTokenUseCase;
import io.perfume.api.common.jwt.JwtProperties;
import io.perfume.api.common.signIn.UserPrincipal;
import io.perfume.api.user.application.port.in.CreateUserUseCase;
import io.perfume.api.user.application.port.in.FindUserUseCase;
import io.perfume.api.user.application.port.in.dto.SignUpGeneralUserCommand;
import io.perfume.api.user.application.port.in.dto.UserResult;
import io.perfume.api.user.application.port.out.UserQueryRepository;
import io.perfume.api.user.domain.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends AbstractAuthenticationTargetUrlRequestHandler implements AuthenticationSuccessHandler {

    private final UserQueryRepository userQueryRepository;

    private final JwtProperties jwtProperties;

    private final FindUserUseCase findUserUseCase;

    private final CreateUserUseCase createUserUseCase;

    private final MakeNewTokenUseCase makeNewTokenUseCase;

    private final Generator generator;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, @NotNull Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        UserResult userResult = newUserIfNotExists(oAuth2User);

        setResponseToken(response, userResult, (UserPrincipal) authentication.getPrincipal());

        String targetUrl = getRedirectUri();
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    private void setResponseToken(HttpServletResponse response, UserResult userResult, UserPrincipal principal) {
        User user = userQueryRepository.findByUsername(userResult.email()).orElseThrow();
        String accessToken = makeNewTokenUseCase.createAccessToken(principal);
        response.setHeader("Authorization", "Bearer " + accessToken);

        String refreshToken = makeNewTokenUseCase.createRefreshToken(user);
        Cookie cookie = new Cookie("X-Refresh-Token", refreshToken);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    @NotNull
    private String getRedirectUri() {
        return UriComponentsBuilder.fromHttpUrl(jwtProperties.redirectUri())
                .build()
                .toUriString();
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
