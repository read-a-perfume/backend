package io.perfume.api.review.adapter.out.persistence.repository.tag;

import io.perfume.api.review.adapter.out.persistence.entity.TagEntity;
import org.springframework.data.repository.CrudRepository;

public interface TagJpaRepository extends CrudRepository<TagEntity, Long> {
}
