package io.perfume.api.auth.application.port.out;

import io.perfume.api.auth.domain.RefreshToken;

public interface RememberMeRepository {
    RefreshToken saveRefreshToken(RefreshToken refreshToken);
    void RemoveRememberMe(String accessToken);
}
