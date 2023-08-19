package io.perfume.api.review.application.out;

import io.perfume.api.review.adapter.out.persistence.entity.ReviewTagEntity;
import io.perfume.api.review.adapter.out.persistence.entity.ReviewTagId;
import org.springframework.data.repository.CrudRepository;

public interface ReviewTagRepository extends CrudRepository<ReviewTagEntity, ReviewTagId> {
}
