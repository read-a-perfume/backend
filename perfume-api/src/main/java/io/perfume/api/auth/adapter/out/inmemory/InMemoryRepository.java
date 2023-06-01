package io.perfume.api.auth.adapter.out.inmemory;

import io.perfume.api.auth.domain.RefreshToken;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Optional;

@Component
class InMemoryRepository {
    private final HashMap<String, RefreshToken> db = new HashMap();

    Optional<RefreshToken> findByAccessToken(String accessToken) {
        return Optional.ofNullable(db.getOrDefault(accessToken, null));
    }

    RefreshToken save(RefreshToken refreshToken) {
        return db.put(refreshToken.getAccessToken(), refreshToken);
    }
}
