package io.perfume.api.auth.application.port.out;

import io.perfume.api.auth.domain.RefreshToken;
import java.util.UUID;

public interface RememberMeRepository {
  RefreshToken saveRefreshToken(RefreshToken refreshToken);

  void removeRefreshToken(UUID tokenId);
}
