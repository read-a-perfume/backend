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

  @Override
  public void verify(String email, String code) {
    String savedCode = stringRedisTemplate.opsForValue().get(email);
    if (savedCode == null) {
      throw new CodeNotFoundException("이메일에 대한 인증 코드가 존재하지 않습니다. email : " + email);
    }
    if (!code.equals(savedCode)) {
      throw new CodeNotFoundException("이메일 인증 코드가 일치하지 않습니다.");
    }
  }
}
