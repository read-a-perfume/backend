package io.perfume.api.perfume.application.port.in;

public interface UserUnFavoritePerfumeUseCase {
  void unFavoritePerfume(Long authorId, Long perfumeId);
}
