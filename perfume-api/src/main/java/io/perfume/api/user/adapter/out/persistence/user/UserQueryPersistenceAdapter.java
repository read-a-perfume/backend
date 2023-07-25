package io.perfume.api.user.adapter.out.persistence.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.user.application.port.out.UserQueryRepository;
import io.perfume.api.user.domain.User;

import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class UserQueryPersistenceAdapter implements UserQueryRepository {

  private final JPAQueryFactory jpaQueryFactory;
  private final UserMapper userMapper;

  @Override
  public Optional<User> findOneByEmail(String email) {
    UserJpaEntity userJpaEntity = jpaQueryFactory.selectFrom(QUserJpaEntity.userJpaEntity)
            .where(
                    QUserJpaEntity.userJpaEntity.email.eq(email)
                            .and(QUserJpaEntity.userJpaEntity.deletedAt.isNull()))
            .fetchOne();
    return Optional.ofNullable(userMapper.toUser(userJpaEntity));
  }

  @Override
  public Optional<User> loadUser(long userId) {
    UserJpaEntity userJpaEntity = jpaQueryFactory.selectFrom(QUserJpaEntity.userJpaEntity)
            .where(
                    QUserJpaEntity.userJpaEntity.id.eq(userId)
                            .and(QUserJpaEntity.userJpaEntity.deletedAt.isNull()))
            .fetchOne();
    return Optional.ofNullable(userMapper.toUser(userJpaEntity));
  }

  @Override
  public Optional<User> findByUsername(String username) {
    UserJpaEntity userJpaEntity = jpaQueryFactory.selectFrom(QUserJpaEntity.userJpaEntity)
            .where(
                    QUserJpaEntity.userJpaEntity.username.eq(username)
                            .and(QUserJpaEntity.userJpaEntity.deletedAt.isNull()))
            .fetchOne();
    return Optional.ofNullable(userMapper.toUser(userJpaEntity));
  }

  @Override
  public Optional<User> findOneByEmailAndUsername(String email, String username) {
    UserJpaEntity userJpaEntity = jpaQueryFactory.selectFrom(QUserJpaEntity.userJpaEntity)
            .where(
                    QUserJpaEntity.userJpaEntity.username.eq(username)
                            .and(QUserJpaEntity.userJpaEntity.email.eq(email))
                            .and(QUserJpaEntity.userJpaEntity.deletedAt.isNull()))
            .fetchOne();
    return Optional.ofNullable(userMapper.toUser(userJpaEntity));
  }

  @Override
  public Optional<User> findOneByEmailOrUsername(String emailOrUsername) {
    UserJpaEntity userJpaEntity = jpaQueryFactory.selectFrom(QUserJpaEntity.userJpaEntity)
            .where(
                    QUserJpaEntity.userJpaEntity.username.eq(emailOrUsername)
                            .or(QUserJpaEntity.userJpaEntity.email.eq(emailOrUsername))
                            .and(QUserJpaEntity.userJpaEntity.deletedAt.isNull()))
            .fetchOne();

    if (Objects.isNull(userJpaEntity)) {
      return Optional.empty();
    }

    return Optional.of(userMapper.toUser(userJpaEntity));
  }
}
