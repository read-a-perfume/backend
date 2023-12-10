package io.perfume.api.user.adapter.out.persistence.user;

import static io.perfume.api.user.adapter.out.persistence.user.QUserEntity.userEntity;

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

  @Override
  public Boolean existByUsernameOrEmail(String username, String email) {
    return jpaQueryFactory
            .selectFrom(userEntity)
            .where(
                userEntity.deletedAt.isNull(),
                userEntity.username.eq(username).or(userEntity.email.eq(email)))
            .fetchFirst()
        != null;
  }
}
