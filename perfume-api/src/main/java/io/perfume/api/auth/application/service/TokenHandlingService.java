package io.perfume.api.auth.application.service;

import io.perfume.api.auth.application.exception.FailedMakeNewAccessTokenException;
import io.perfume.api.auth.application.port.in.MakeNewTokenUseCase;
import io.perfume.api.auth.application.port.in.dto.ReissuedTokenResult;
import io.perfume.api.auth.application.port.out.RememberMeQueryRepository;
import io.perfume.api.auth.application.port.out.RememberMeRepository;
import io.perfume.api.auth.domain.RefreshToken;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenHandlingService implements MakeNewTokenUseCase {
  private final RememberMeQueryRepository rememberMeQueryRepository;
  private final RememberMeRepository rememberMeRepository;
  private final AuthenticationTokenService authenticationTokenService;

  @Override
  public ReissuedTokenResult reissueAccessToken(
      String accessToken, String refreshToken, LocalDateTime now) {
    validateToken(accessToken, refreshToken, now);

    RefreshToken refreshTokenDomain =
        authenticationTokenService.getRefreshTokenFromClient(refreshToken);
    verifyTokenMatch(refreshTokenDomain);

    String newAccessToken =
        authenticationTokenService.reissueAccessToken(refreshTokenDomain.getUserId(), now);
    String newRefreshToken = reissueRefreshToken(refreshTokenDomain);

    return new ReissuedTokenResult(newAccessToken, newRefreshToken);
  }

  private void validateToken(String accessToken, String refreshToken, LocalDateTime now) {
    boolean isExpiredAccessToken = authenticationTokenService.isExpired(accessToken, now);
    if (!isExpiredAccessToken) {
      throw new IllegalArgumentException("Access token not expired yet.");
    }

    boolean isValidRefreshToken = authenticationTokenService.isValid(refreshToken, now);
    if (!isValidRefreshToken) {
      throw new IllegalArgumentException("Refresh token is invalid.");
    }
  }

  @NotNull
  private String reissueRefreshToken(RefreshToken refreshToken) {
    rememberMeRepository.removeRefreshToken(refreshToken.getTokenId());
    RefreshToken newRefreshToken = new RefreshToken(refreshToken.getUserId());
    rememberMeRepository.saveRefreshToken(newRefreshToken);

    return authenticationTokenService.createRefreshToken(
        newRefreshToken.getTokenId(), newRefreshToken.getUserId(), LocalDateTime.now());
  }

  @Override
  public String createAccessToken(Long userId, LocalDateTime now) {
    return authenticationTokenService.createAccessToken(userId, now);
  }

  @Override
  public String createRefreshToken(Long userId, LocalDateTime now) {
    RefreshToken refreshToken = rememberMeRepository.saveRefreshToken(new RefreshToken(userId));
    return authenticationTokenService.createRefreshToken(
        refreshToken.getTokenId(), refreshToken.getUserId(), now);
  }

  private void verifyTokenMatch(RefreshToken refreshToken) {
    RefreshToken refreshTokenFromRedis =
        rememberMeQueryRepository.getRefreshTokenById(refreshToken.getTokenId()).orElse(null);

    if (!refreshToken.equals(refreshTokenFromRedis)) {
      throw new FailedMakeNewAccessTokenException();
    }
  }
}
