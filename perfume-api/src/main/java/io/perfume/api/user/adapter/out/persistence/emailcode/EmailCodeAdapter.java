package io.perfume.api.user.adapter.out.persistence.emailcode;

import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.user.application.port.out.EmailCodeRepository;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;

@PersistenceAdapter
@RequiredArgsConstructor
public class EmailCodeAdapter implements EmailCodeRepository {

  private final StringRedisTemplate stringRedisTemplate;

  @Override
  public void save(String email, String code, Duration duration) {
    stringRedisTemplate.opsForValue().set(email, code, duration);
  }
}
