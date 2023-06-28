package io.perfume.api.auth.application.port.out;

import io.perfume.api.auth.domain.RefreshToken;

import java.util.Optional;
import java.util.UUID;

public interface RememberMeQueryRepository {
    Optional<RefreshToken> getRefreshTokenById(UUID tokenId);
}
