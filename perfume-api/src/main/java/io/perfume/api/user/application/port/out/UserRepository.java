package io.perfume.api.user.application.port.out;

import io.perfume.api.user.domain.User;
import java.util.Optional;

public interface UserRepository {

  Optional<User> save(User user);

  Boolean existByUsernameOrEmail(String username, String email);
}
