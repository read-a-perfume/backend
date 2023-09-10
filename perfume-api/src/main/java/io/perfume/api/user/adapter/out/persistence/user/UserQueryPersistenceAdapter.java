package io.perfume.api.user.adapter.out.persistence.user;

import static io.perfume.api.user.adapter.out.persistence.user.QUserEntity.userEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.user.application.port.out.UserQueryRepository;
import io.perfume.api.user.domain.User;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class UserQueryPersistenceAdapter implements UserQueryRepository {

  private final JPAQueryFactory jpaQueryFactory;

  private final UserMapper userMapper;

  @Override
  public Optional<User> findOneByEmail(String email) {
    UserEntity entity = jpaQueryFactory.selectFrom(QUserEntity.userEntity)
        .where(
            QUserEntity.userEntity.email.eq(email)
                .and(QUserEntity.userEntity.deletedAt.isNull()))
        .fetchOne();

    return Optional.ofNullable(userMapper.toUser(entity));
  }

  @Override
  public Optional<User> loadUser(long userId) {
    UserEntity entity = jpaQueryFactory.selectFrom(QUserEntity.userEntity)
        .where(
            QUserEntity.userEntity.id.eq(userId)
                .and(QUserEntity.userEntity.deletedAt.isNull()))
        .fetchOne();

    return Optional.ofNullable(userMapper.toUser(entity));
  }

  @Override
  public Optional<User> findByUsername(String username) {
    UserEntity entity = jpaQueryFactory.selectFrom(QUserEntity.userEntity)
        .where(
            QUserEntity.userEntity.username.eq(username)
                .and(QUserEntity.userEntity.deletedAt.isNull()))
        .fetchOne();

    return Optional.ofNullable(userMapper.toUser(entity));
  }

  @Override
  public Optional<User> findOneByEmailAndUsername(String email, String username) {
    UserEntity entity = jpaQueryFactory.selectFrom(QUserEntity.userEntity)
        .where(
            QUserEntity.userEntity.username.eq(username)
                .and(QUserEntity.userEntity.email.eq(email))
                .and(QUserEntity.userEntity.deletedAt.isNull()))
        .fetchOne();

    return Optional.ofNullable(userMapper.toUser(entity));
  }

  @Override
  public Optional<User> findOneByEmailOrUsername(String emailOrUsername) {
    UserEntity entity = jpaQueryFactory.selectFrom(QUserEntity.userEntity)
        .where(
            QUserEntity.userEntity.username.eq(emailOrUsername)
                .or(QUserEntity.userEntity.email.eq(emailOrUsername))
                .and(QUserEntity.userEntity.deletedAt.isNull()))
        .fetchOne();

    return Optional.ofNullable(userMapper.toUser(entity));
  }

  @Override
  public List<User> findUsersByIds(List<Long> userIds) {
    return jpaQueryFactory
        .selectFrom(userEntity)
        .where(
            userEntity.id.in(userIds)
                .and(userEntity.deletedAt.isNull()))
        .fetch()
        .stream()
        .map(userMapper::toUser)
        .toList();
  }
}
