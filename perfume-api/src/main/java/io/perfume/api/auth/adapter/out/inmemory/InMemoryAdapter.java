package io.perfume.api.auth.adapter.out.inmemory;

import io.perfume.api.auth.application.port.out.RememberMeQueryRepository;
import io.perfume.api.auth.application.port.out.RememberMeRepository;
import io.perfume.api.auth.domain.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Profile({"dev", "test"})
@Repository
@RequiredArgsConstructor
class InMemoryAdapter implements RememberMeQueryRepository, RememberMeRepository {
    // TODO : 괜찮은 InMemory 찾아보기
    private final InMemoryRepository inMemoryRepository;
    @Override
    public Optional<RefreshToken> getRefreshToken(String accessToken) {
        return inMemoryRepository.findByAccessToken(accessToken);
    }

    @Override
    public RefreshToken saveRefreshToken(RefreshToken refreshToken) {
        return inMemoryRepository.save(refreshToken);
    }

    @Override
    public void RemoveRememberMe(String accessToken) {

    }
}
