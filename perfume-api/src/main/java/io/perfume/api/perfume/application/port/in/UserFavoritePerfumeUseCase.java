package io.perfume.api.perfume.application.port.in;

public interface UserFavoritePerfumeUseCase {

  void addAndDeleteFavoritePerfume(Long authorId, Long perfumeId);

}
