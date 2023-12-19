package io.perfume.api.user.application.port.out;

import io.perfume.api.user.domain.User;

public interface UserRepository {

  User save(User user);

  boolean existByUsernameOrEmail(String username, String email);
}
