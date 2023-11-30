package io.perfume.api.review.adapter.out.persistence.repository.comment;

import io.perfume.api.review.adapter.out.persistence.entity.ReviewCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewCommentJpaRepository extends JpaRepository<ReviewCommentEntity, Long> {
}
