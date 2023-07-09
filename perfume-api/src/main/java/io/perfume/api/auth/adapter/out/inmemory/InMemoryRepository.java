package io.perfume.api.auth.adapter.out.inmemory;

import io.perfume.api.auth.domain.RefreshToken;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
class InMemoryRepository {
  private final HashMap<UUID, RefreshToken> db = new HashMap<>();

  Optional<RefreshToken> findByAccessToken(UUID tokenId) {
    return Optional.ofNullable(db.getOrDefault(tokenId, null));
  }

  RefreshToken save(RefreshToken refreshToken) {
    db.put(refreshToken.getTokenId(), refreshToken);
    return db.get(refreshToken.getTokenId());
  }

  void delete(UUID tokenId) {
    db.remove(tokenId);
  }

}
