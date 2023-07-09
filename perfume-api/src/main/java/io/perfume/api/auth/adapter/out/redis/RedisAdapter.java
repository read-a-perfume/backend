package io.perfume.api.auth.adapter.out.redis;

import io.perfume.api.auth.application.exception.NotFoundRefreshTokenException;
import io.perfume.api.auth.application.port.out.RememberMeQueryRepository;
import io.perfume.api.auth.application.port.out.RememberMeRepository;
import io.perfume.api.auth.domain.RefreshToken;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Profile("prod")
@Repository
@RequiredArgsConstructor
public class RedisAdapter implements RememberMeQueryRepository, RememberMeRepository {
  private final RedisRepository redisRepository;

  @Override
  public RefreshToken saveRefreshToken(RefreshToken refreshToken) {
    RedisRefreshToken save = redisRepository.save(RedisRefreshToken.fromRefreshToken(refreshToken));
    return RefreshToken.fromRedisRefreshToken(save);
  }

  @Override
  public void removeRefreshToken(UUID tokenId) {
    redisRepository.deleteById(tokenId);
  }

  @Override
  public Optional<RefreshToken> getRefreshTokenById(UUID tokenId) {
    RedisRefreshToken token =
        redisRepository.findById(tokenId).orElseThrow(NotFoundRefreshTokenException::new);
    return Optional.ofNullable(RefreshToken.fromRedisRefreshToken(token));
  }
}
