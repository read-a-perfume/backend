package io.perfume.api.review.adapter.out.persistence.repository;

import io.perfume.api.review.adapter.out.persistence.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
}
