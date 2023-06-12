package io.perfume.api.auth.application.port.out;

import io.perfume.api.auth.domain.RefreshToken;

import java.sql.Ref;

public interface RememberMeRepository {
    RefreshToken saveRefreshToken(RefreshToken refreshToken);
    void removeRememberMe(String accessToken);
}
