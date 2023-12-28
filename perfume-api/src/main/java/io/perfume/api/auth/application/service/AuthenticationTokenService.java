package io.perfume.api.auth.application.service;

import io.perfume.api.auth.domain.RefreshToken;
import io.perfume.api.common.auth.Constants;
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

  private final JsonWebTokenGenerator jsonWebTokenGenerator;

  private final JwtProperties jwtProperties;

  public String createAccessToken(Long userId, LocalDateTime now) {
    return jsonWebTokenGenerator.create(
        Constants.ACCESS_TOKEN_KEY,
        Map.of(Constants.USER_ID_KEY, userId, "roles", List.of("ROLE_USER")),
        jwtProperties.accessTokenValidityInSeconds(),
        now);
  }

  public String reissueAccessToken(Long userId, LocalDateTime now) {
    return jsonWebTokenGenerator.create(
        Constants.ACCESS_TOKEN_KEY,
        Map.of(Constants.USER_ID_KEY, userId, "roles", List.of("ROLE_USER")),
        jwtProperties.accessTokenValidityInSeconds(),
        now);
  }

  public String createRefreshToken(UUID tokenId, Long userId, LocalDateTime now) {
    return jsonWebTokenGenerator.create(
        Constants.REFRESH_TOKEN_KEY,
        Map.of(Constants.TOKEN_ID_KEY, tokenId, Constants.USER_ID_KEY, userId),
        jwtProperties.refreshTokenValidityInSeconds(),
        now);
  }

  public RefreshToken getRefreshTokenFromClient(String refreshToken) {
    UUID tokenId =
        UUID.fromString(
            jsonWebTokenGenerator.getClaim(refreshToken, Constants.TOKEN_ID_KEY, String.class));
    Long userId = jsonWebTokenGenerator.getClaim(refreshToken, Constants.USER_ID_KEY, Long.class);

    return RefreshToken.create(tokenId, userId);
  }

  public boolean isValid(String token, LocalDateTime now) {
    if (Objects.isNull(token)) {
      return false;
    }

    return jsonWebTokenGenerator.verify(token, now);
  }

  public boolean isExpired(String token, LocalDateTime now) {
    if (Objects.isNull(token)) {
      return false;
    }

    return jsonWebTokenGenerator.isExpired(token, now);
  }
}
