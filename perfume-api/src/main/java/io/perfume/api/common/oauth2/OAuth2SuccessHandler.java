package io.perfume.api.common.oauth2;

import io.perfume.api.common.properties.JsonWebTokenProperties;
import jakarta.servlet.ServletException;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends AbstractAuthenticationTargetUrlRequestHandler implements AuthenticationSuccessHandler {

    private final JsonWebTokenGenerator jsonWebTokenGenerator;

    private final JsonWebTokenProperties jsonWebTokenProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String accessToken = createJsonWebToken(oAuth2User);
        String targetUrl = getRedirectUri(accessToken);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    @NotNull
    private String getRedirectUri(String accessToken) {
        return UriComponentsBuilder.fromHttpUrl(jsonWebTokenProperties.redirectUri())
                .queryParam("accessToken", accessToken)
                .build()
                .toUriString();
    }

    @NotNull
    private String createJsonWebToken(OAuth2User oAuth2User) {
        return jsonWebTokenGenerator.create(
                oAuth2User.getAttributes().get("email").toString(),
                Map.of("roles", List.of("ROLE_USER")),
                jsonWebTokenProperties.accessTokenValidityInSeconds(),
                LocalDateTime.now()
        );
    }
}
