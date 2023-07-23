package io.perfume.api.user.application.port.out;

import io.perfume.api.user.domain.User;
import java.util.Optional;

public interface UserQueryRepository {

  Optional<User> loadUser(long userId);

  Optional<User> findByUsername(String username);

  Optional<User> findOneByEmail(String email);

  Optional<User> findOneByEmailAndUsername(String email, String username);

  Optional<User> findOneByEmailOrUsername(String emailOrUsername);
}
