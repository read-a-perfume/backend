package io.perfume.api.perfume.application.port.in;

public interface UserUnFollowPerfumeUseCase {
  void unFollowPerfume(Long authorId, Long perfumeId);
}
