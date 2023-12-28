package io.perfume.api.auth.adapter.out.inmemory;

import io.perfume.api.auth.application.port.out.RememberMeQueryRepository;
import io.perfume.api.auth.application.port.out.RememberMeRepository;
import io.perfume.api.auth.domain.RefreshToken;
import io.perfume.api.base.PersistenceAdapter;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;

@Profile("!prod")
@RequiredArgsConstructor
@PersistenceAdapter
class InMemoryAdapter implements RememberMeQueryRepository, RememberMeRepository {
  private final InMemoryRepository inMemoryRepository;

  @Override
  public Optional<RefreshToken> getRefreshTokenById(UUID tokenId) {
    return inMemoryRepository.findByAccessToken(tokenId);
  }

  @Override
  public RefreshToken saveRefreshToken(RefreshToken refreshToken) {
    return inMemoryRepository.save(refreshToken);
  }

  @Override
  public void removeRefreshToken(UUID tokenId) {
    inMemoryRepository.delete(tokenId);
  }
}
