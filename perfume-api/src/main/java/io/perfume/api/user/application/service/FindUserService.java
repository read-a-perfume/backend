package io.perfume.api.user.application.service;

import io.perfume.api.user.application.port.in.FindUserUseCase;
import io.perfume.api.user.application.port.in.dto.UserResult;
import io.perfume.api.user.application.port.out.UserQueryRepository;
import io.perfume.api.user.domain.User;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindUserService implements FindUserUseCase {

  private final UserQueryRepository userQueryRepository;

  @Override
  public Optional<UserResult> findOneByEmail(String email) {
    return userQueryRepository.findOneByEmail(email).map(this::toDto);
  }

  private UserResult toDto(User user) {
    return new UserResult(user.getId(), user.getUsername(), user.getEmail(), user.getName(),
        user.getCreatedAt());
  }
}
