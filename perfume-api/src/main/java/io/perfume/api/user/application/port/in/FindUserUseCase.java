package io.perfume.api.user.application.port.in;

import io.perfume.api.user.application.port.in.dto.UserResult;
import java.util.Optional;

public interface FindUserUseCase {

  Optional<UserResult> findOneByEmail(String email);
}
