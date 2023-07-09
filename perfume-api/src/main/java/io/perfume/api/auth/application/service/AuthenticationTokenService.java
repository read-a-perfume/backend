package io.perfume.api.auth.application.service;

import io.perfume.api.auth.domain.RefreshToken;
import io.perfume.api.common.jwt.JwtProperties;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import jwt.JsonWebTokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationTokenService {

  static final String ACCESS_TOKEN_NAME = "access_token";
  static final String REFRESH_TOKEN_NAME = "refresh_token";
  private static final String TOKEN_PREFIX = "Bearer ";

  private final JsonWebTokenGenerator jsonWebTokenGenerator;
  private final JwtProperties jwtProperties;

  public String createAccessToken(Long userId, LocalDateTime now) {
    return TOKEN_PREFIX + jsonWebTokenGenerator.create(
        ACCESS_TOKEN_NAME,
        Map.of("userId", userId, "roles", List.of("ROLE_USER")),
        jwtProperties.accessTokenValidityInSeconds(),
        now);
  }

  public String reissueAccessToken(String accessToken, LocalDateTime now) {
    Long userId = getUserIdFromToken(accessToken);
    return jsonWebTokenGenerator.create(
        ACCESS_TOKEN_NAME,
        Map.of("userId", userId, "roles", List.of("ROLE_USER")),
        jwtProperties.accessTokenValidityInSeconds(),
        now);
  }

  public String createRefreshToken(UUID tokenId, Long userId, LocalDateTime now) {
    return jsonWebTokenGenerator.create(
        REFRESH_TOKEN_NAME,
        Map.of("tokenId", tokenId, "userId", userId),
        jwtProperties.refreshTokenValidityInSeconds(),
        now);
  }

  public Long getUserIdFromToken(String accessToken) {
    String token = jsonWebTokenGenerator.validAccessToken(accessToken);

    return jsonWebTokenGenerator.getClaim(token, "userId", Long.class);
  }

  public RefreshToken getRefreshTokenFromClient(String refreshToken) {
    UUID tokenId = jsonWebTokenGenerator.getClaim(refreshToken, "tokenId", UUID.class);
    Long userId = jsonWebTokenGenerator.getClaim(refreshToken, "userId", Long.class);

    return RefreshToken.create(tokenId, userId);
  }

  public boolean isValid(String token, LocalDateTime now) {
    if (Objects.isNull(token)) {
      return false;
    }

    return jsonWebTokenGenerator.verify(token, now);
  }
}
