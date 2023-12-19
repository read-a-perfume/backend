package io.perfume.api.user.application.port.in;

import java.time.LocalDateTime;

public interface SetUserProfileUseCase {

  long updateUserThumbnail(long userId, byte[] image, LocalDateTime now);
}
