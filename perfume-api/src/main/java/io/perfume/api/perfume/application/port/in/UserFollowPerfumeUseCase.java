package io.perfume.api.perfume.application.port.in;

import java.time.LocalDateTime;

public interface UserFollowPerfumeUseCase {
  void followPerfume(Long authorId, Long perfumeId);
}
