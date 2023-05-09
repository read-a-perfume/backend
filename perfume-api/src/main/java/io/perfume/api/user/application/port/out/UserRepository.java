package io.perfume.api.user.application.port.out;

import io.perfume.api.user.domain.User;

public interface UserRepository {

    User loadUser(long UserId);
}
