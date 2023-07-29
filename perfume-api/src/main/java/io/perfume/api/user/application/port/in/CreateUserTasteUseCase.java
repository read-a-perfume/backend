package io.perfume.api.user.application.port.in;

import io.perfume.api.user.application.port.in.dto.UserTasteResult;

public interface CreateUserTasteUseCase {

  UserTasteResult createUserTaste(Long userId, Long noteId);
}
