package io.perfume.api.auth.application.service;

import io.perfume.api.auth.application.exception.FailedMakeNewAccessTokenException;
import io.perfume.api.auth.application.exception.NotFoundRefreshTokenException;
import io.perfume.api.auth.application.port.in.MakeNewAccessTokenUseCase;
import io.perfume.api.auth.application.port.out.RememberMeQueryRepository;
import io.perfume.api.auth.application.port.out.RememberMeRepository;
import io.perfume.api.auth.domain.RefreshToken;
import io.perfume.api.common.property.JsonWebTokenProperties;
import jwt.JsonWebTokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * token 발급, 무효같은 기능을 제공
 */
@Service
@RequiredArgsConstructor
public class TokenHandlingService implements MakeNewAccessTokenUseCase {
    private final RememberMeQueryRepository rememberMeQueryRepository;
    private final RememberMeRepository rememberMeRepository;
    private final JsonWebTokenGenerator tokenGenerator;
    private final JsonWebTokenProperties jsonWebTokenProperties;
    /**
     * RefreshToken을 확인하고 새로운 AccessToken을 반환
     *
     * @param accessToken 기존에 사용한 token
     * @return 새로운 token
     */
    @Override
    public String makeNewAccessToken(String accessToken) {
        RefreshToken refreshToken = rememberMeQueryRepository
                .getRefreshToken(accessToken)
                .orElseThrow(NotFoundRefreshTokenException::new);
        rememberMeRepository.removeRememberMe(accessToken);

        if(refreshToken.canIssueAccessToken(LocalDateTime.now())) {
            String newAccessToken = tokenGenerator.create(
                    tokenGenerator.getSubject(accessToken),
                    Map.of("roles", List.of("ROLE_USER")),
                    jsonWebTokenProperties.accessTokenValidityInSeconds(),
                    LocalDateTime.now());

            refreshToken.updateAccessToken(newAccessToken);
            return rememberMeRepository
                    .saveRefreshToken(refreshToken)
                    .getAccessToken();

        } else {
            throw new FailedMakeNewAccessTokenException();
        }
    }
}
