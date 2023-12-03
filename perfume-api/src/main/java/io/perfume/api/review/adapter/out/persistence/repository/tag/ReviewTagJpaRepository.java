package io.perfume.api.review.adapter.out.persistence.repository.tag;

import io.perfume.api.review.adapter.out.persistence.entity.ReviewTagEntity;
import io.perfume.api.review.adapter.out.persistence.entity.ReviewTagId;
import org.springframework.data.repository.CrudRepository;

public interface ReviewTagJpaRepository extends CrudRepository<ReviewTagEntity, ReviewTagId> {}
