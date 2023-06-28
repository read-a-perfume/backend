package io.perfume.api.auth.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import encryptor.impl.JwtUtil;
import io.perfume.api.auth.application.exception.FailedMakeNewAccessTokenException;
import io.perfume.api.auth.application.exception.FailedMakeRefreshTokenException;
import io.perfume.api.auth.application.exception.NotFoundRefreshTokenException;
import io.perfume.api.auth.application.port.in.MakeNewTokenUseCase;
import io.perfume.api.auth.application.port.out.RememberMeQueryRepository;
import io.perfume.api.auth.application.port.out.RememberMeRepository;
import io.perfume.api.auth.domain.RefreshToken;
import io.perfume.api.common.jwt.JwtProperties;
import io.perfume.api.common.signIn.UserPrincipal;
import io.perfume.api.user.domain.User;
import jwt.JsonWebTokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * token 발급, 무효같은 기능을 제공
 */
@Service
@RequiredArgsConstructor
public class TokenHandlingService implements MakeNewTokenUseCase {
    private final RememberMeQueryRepository rememberMeQueryRepository;
    private final RememberMeRepository rememberMeRepository;
    private final JsonWebTokenGenerator tokenGenerator;
    private final JwtUtil jwtUtil;
    private final JwtProperties jwtProperties;
    private final ObjectMapper objectMapper;
    /**
     * RefreshToken을 확인하고 새로운 AccessToken을 반환
     *
     * @param accessToken 기존에 인증을 위해 사용한 token
     * @param refreshToken 재발행을 위한 token
     * @return 새로운 accessToken
     */
    @Override
    public String reissueAccessToken(String accessToken, String refreshToken) {
        RefreshToken refreshTokenFromClient;
        try {
            refreshTokenFromClient = objectMapper.readValue(refreshToken, RefreshToken.class);
        } catch(JsonProcessingException e) {
            throw new FailedMakeNewAccessTokenException(e);
        }

        RefreshToken refreshTokenFromRedis = rememberMeQueryRepository
                .getRefreshTokenById(refreshTokenFromClient.getTokenId())
                .orElseThrow(NotFoundRefreshTokenException::new);

        if (!refreshTokenFromRedis.equals(refreshTokenFromClient)) {
           throw new FailedMakeNewAccessTokenException();
        }
        rememberMeRepository.removeRefreshToken(refreshTokenFromRedis.getTokenId());

        RefreshToken newRefreshToken = new RefreshToken(refreshTokenFromRedis.getUserId());
        rememberMeRepository.saveRefreshToken(newRefreshToken);

        return tokenGenerator.create(
                tokenGenerator.getSubject(accessToken),
                Map.of("roles", List.of("ROLE_USER")),
                jwtProperties.accessTokenValidityInSeconds(),
                LocalDateTime.now());
    }

    @Override
    public String createAccessToken(UserPrincipal principal) {
        List<String> authorities = principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        // TODO: jwtUtil 사용하지 않으면서 이메일 로그인 시 access token 생성
        return jwtUtil.create(principal.getUsername(), authorities, LocalDateTime.now());
    }

    @Override
    public String createRefreshToken(User user) {
        Long userId = user.getId();
        RefreshToken refreshToken = rememberMeRepository.saveRefreshToken(new RefreshToken(userId));
        try {
            return objectMapper.writeValueAsString(refreshToken);
        } catch (JsonProcessingException e) {
            throw new FailedMakeRefreshTokenException();
        }
    }
}

// oauthSuccessHandler
