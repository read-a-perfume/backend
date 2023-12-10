package io.perfume.api.auth.application.service;

import io.perfume.api.auth.application.exception.FailedMakeNewAccessTokenException;
import io.perfume.api.auth.application.exception.NotFoundRefreshTokenException;
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
    boolean isValidTokens = authenticationTokenService.isValid(refreshToken, now);
    if (isValidTokens) {
      throw new IllegalArgumentException("Invalid tokens");
    }

    RefreshToken refreshTokenDomain =
        authenticationTokenService.getRefreshTokenFromClient(refreshToken);
    verifyTokenMatch(refreshTokenDomain);
    RefreshToken newRefreshToken = reissueRefreshToken(refreshTokenDomain);
    return getReissuedTokenResult(accessToken, newRefreshToken, now);
  }

  @NotNull
  private ReissuedTokenResult getReissuedTokenResult(
      String accessToken, RefreshToken newRefreshToken, LocalDateTime now) {
    String accessTokenResult = authenticationTokenService.reissueAccessToken(accessToken, now);
    String refreshTokenResult =
        authenticationTokenService.createRefreshToken(
            newRefreshToken.getTokenId(), newRefreshToken.getUserId(), LocalDateTime.now());

    return new ReissuedTokenResult(accessTokenResult, refreshTokenResult);
  }

  @NotNull
  private RefreshToken reissueRefreshToken(RefreshToken refreshToken) {
    rememberMeRepository.removeRefreshToken(refreshToken.getTokenId());
    RefreshToken newRefreshToken = new RefreshToken(refreshToken.getUserId());
    rememberMeRepository.saveRefreshToken(newRefreshToken);
    return newRefreshToken;
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
        rememberMeQueryRepository
            .getRefreshTokenById(refreshToken.getTokenId())
            .orElseThrow(NotFoundRefreshTokenException::new);

    if (!refreshTokenFromRedis.equals(refreshToken)) {
      throw new FailedMakeNewAccessTokenException();
    }
  }
}
