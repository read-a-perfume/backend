package io.perfume.api.user.adapter.out.persistence.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.user.application.port.out.UserRepository;
import io.perfume.api.user.domain.User;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@PersistenceAdapter
public class UserPersistenceAdapter implements UserRepository {

  private final UserJpaRepository userJpaRepository;

  private final JPAQueryFactory jpaQueryFactory;
  private final UserMapper userMapper;

  @Override
  @Transactional
  public Optional<User> save(User user) {
    UserEntity userEntity = userMapper.toUserJpaEntity(user);
    userJpaRepository.save(userEntity);
    return Optional.ofNullable(userMapper.toUser(userEntity));
  }
}
