package io.perfume.api.auth.adapter.out.redis;

import io.perfume.api.auth.application.port.out.RememberMeQueryRepository;
import io.perfume.api.auth.application.port.out.RememberMeRepository;
import io.perfume.api.auth.domain.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Profile("prod")
@Repository
@RequiredArgsConstructor
public class RedisAdapter implements RememberMeQueryRepository, RememberMeRepository {
    private final RedisRepository redisRepository;
    @Override
    public Optional<RefreshToken> getRefreshToken(String accessToken) {
        return Optional.empty();
    }

    @Override
    public RefreshToken saveRefreshToken(RefreshToken refreshToken) {
        return null;
    }

    @Override
    public void RemoveRememberMe(String accessToken) {

    }
}
