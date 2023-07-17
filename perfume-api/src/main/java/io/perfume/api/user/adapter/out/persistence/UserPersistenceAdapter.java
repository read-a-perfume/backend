package io.perfume.api.user.adapter.out.persistence;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.user.application.port.out.UserRepository;
import io.perfume.api.user.domain.User;
import java.util.Optional;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@PersistenceAdapter
public class UserPersistenceAdapter implements UserRepository {

  private final UserJpaRepository userJpaRepository;

  private final JPAQueryFactory jpaQueryFactory;
  private final UserMapper userMapper;

  @Override
  public Optional<User> save(User user) {
    UserJpaEntity userJpaEntity = userMapper.toUserJpaEntity(user);
    userJpaRepository.save(userJpaEntity);
    return Optional.ofNullable(userMapper.toUser(userJpaEntity));
  }
}
