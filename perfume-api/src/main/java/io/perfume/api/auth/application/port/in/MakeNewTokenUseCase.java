package io.perfume.api.auth.application.port.in;

import io.perfume.api.auth.application.port.in.dto.ReissuedTokenResult;

import java.time.LocalDateTime;

public interface MakeNewTokenUseCase {
  ReissuedTokenResult reissueAccessToken(String accessToken, String refreshToken,
                                         LocalDateTime now);

  String createAccessToken(Long userId, LocalDateTime now);

  String createRefreshToken(Long userId, LocalDateTime now);
}
