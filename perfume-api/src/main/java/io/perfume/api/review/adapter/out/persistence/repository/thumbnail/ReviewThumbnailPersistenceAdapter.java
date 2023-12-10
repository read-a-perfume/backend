package io.perfume.api.review.adapter.out.persistence.repository.thumbnail;

import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.review.application.out.thumbnail.ReviewThumbnailRepository;
import io.perfume.api.review.domain.ReviewThumbnail;
import java.util.List;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@PersistenceAdapter
public class ReviewThumbnailPersistenceAdapter implements ReviewThumbnailRepository {

  private final ReviewThumbnailJpaRepository reviewTagRepository;
  private final ReviewThumbnailMapper reviewTagMapper;

  @Override
  public ReviewThumbnail save(ReviewThumbnail thumbnail) {
    return reviewTagMapper.toDomain(reviewTagRepository.save(reviewTagMapper.toEntity(thumbnail)));
  }

  @Override
  public List<ReviewThumbnail> saveAll(List<ReviewThumbnail> thumbnails) {
    final List<ReviewThumbnailEntity> entities =
        thumbnails.stream().map(reviewTagMapper::toEntity).toList();

    return StreamSupport.stream(reviewTagRepository.saveAll(entities).spliterator(), true)
        .map(reviewTagMapper::toDomain)
        .toList();
  }
}
