package io.perfume.api.user.stub;

import io.perfume.api.user.application.port.out.UserQueryRepository;
import io.perfume.api.user.application.port.out.UserRepository;
import io.perfume.api.user.domain.User;
import java.util.List;
import java.util.Optional;

public class StubUserRepository implements UserRepository, UserQueryRepository {

  @Override
  public Optional<User> loadUser(long userId) {
    return Optional.empty();
  }

  @Override
  public Optional<User> findByUsername(String username) {
    return Optional.empty();
  }

  @Override
  public User save(User user) {
    return user;
  }

  @Override
  public boolean existByUsernameOrEmail(String username, String email) {
    return false;
  }

  @Override
  public Optional<User> findOneByEmail(String email) {
    return Optional.empty();
  }

  @Override
  public Optional<User> findOneByEmailAndUsername(String email, String id) {
    return Optional.empty();
  }

  @Override
  public Optional<User> findOneByEmailOrUsername(String emailOrUsername) {
    return Optional.empty();
  }

  @Override
  public List<User> findUsersByIds(List<Long> userIds) {
    return null;
  }

  @Override
  public Optional<User> findUserById(long userId) {
    return Optional.empty();
  }
}
