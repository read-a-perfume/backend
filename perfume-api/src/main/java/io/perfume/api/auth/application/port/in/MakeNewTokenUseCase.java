package io.perfume.api.auth.application.port.in;

import io.perfume.api.common.signIn.UserPrincipal;
import io.perfume.api.user.domain.User;

public interface MakeNewTokenUseCase {
    String reissueAccessToken(String accessToken, String refreshToken);
    String createAccessToken(UserPrincipal userPrincipal);
    String createRefreshToken(User user);
}
