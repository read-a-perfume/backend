package io.perfume.api.user.application.port.in;

import io.perfume.api.user.application.port.in.dto.UserTypeResult;
import java.util.List;

public interface UserTypeUseCase {

  void createUserType(Long userId, Long noteId);

  List<UserTypeResult> getUserTypes(Long userId);
}
