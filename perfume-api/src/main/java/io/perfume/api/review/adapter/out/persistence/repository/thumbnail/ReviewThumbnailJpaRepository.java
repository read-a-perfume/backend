package io.perfume.api.review.adapter.out.persistence.repository.thumbnail;

import io.perfume.api.review.adapter.out.persistence.entity.ReviewThumbnailEntity;
import org.springframework.data.repository.CrudRepository;

public interface ReviewThumbnailJpaRepository extends CrudRepository<ReviewThumbnailEntity, Long> {
}
