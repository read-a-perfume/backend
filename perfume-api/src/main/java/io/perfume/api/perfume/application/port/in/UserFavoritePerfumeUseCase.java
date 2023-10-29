package io.perfume.api.perfume.application.port.in;

public interface UserFavoritePerfumeUseCase {
  void favoritePerfume(Long authorId, Long perfumeId);
}
