package io.perfume.api.review.adapter.out.persistence.repository.like;

import io.perfume.api.review.adapter.out.persistence.entity.ReviewLikeEntity;
import org.springframework.data.repository.CrudRepository;

public interface ReviewLikeJpaRepository extends CrudRepository<ReviewLikeEntity, Long> {
}
