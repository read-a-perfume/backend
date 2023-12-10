package io.perfume.api.user.application.port.in;

import io.perfume.api.user.application.port.in.dto.UserProfileResult;
import io.perfume.api.user.application.port.in.dto.UserResult;
import java.util.List;
import java.util.Optional;

public interface FindUserUseCase {

  Optional<UserResult> findOneByEmail(String email);

  Optional<UserResult> findOneBySocialId(String socialId);

  List<UserResult> findUsersByIds(List<Long> userIds);

  Optional<UserResult> findUserById(long userId);

  UserProfileResult findUserProfileById(long userId);
}
