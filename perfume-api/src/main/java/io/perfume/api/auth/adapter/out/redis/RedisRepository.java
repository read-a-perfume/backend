package io.perfume.api.auth.adapter.out.redis;

import io.perfume.api.auth.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RedisRepository extends CrudRepository<RefreshToken, String> {
}
