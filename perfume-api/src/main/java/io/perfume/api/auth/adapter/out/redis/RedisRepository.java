package io.perfume.api.auth.adapter.out.redis;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface RedisRepository extends CrudRepository<RedisRefreshToken, UUID> {
}
