package io.perfume.api.auth.application.port.out;

import io.perfume.api.auth.domain.RefreshToken;

import java.util.Optional;

public interface RememberMeQueryRepository {
    Optional<RefreshToken> getRefreshToken(String accessToken);
}
