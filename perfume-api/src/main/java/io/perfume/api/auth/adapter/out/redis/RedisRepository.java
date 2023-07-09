package io.perfume.api.auth.adapter.out.redis;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface RedisRepository extends CrudRepository<RedisRefreshToken, UUID> {
}
