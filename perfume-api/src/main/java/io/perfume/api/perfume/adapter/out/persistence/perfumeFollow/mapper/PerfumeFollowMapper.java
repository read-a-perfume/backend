package io.perfume.api.perfume.adapter.out.persistence.perfumeFollow.mapper;

import io.perfume.api.perfume.adapter.out.persistence.perfumeFollow.PerfumeFollowJpaEntity;
import io.perfume.api.perfume.domain.PerfumeFollow;
import org.springframework.stereotype.Component;

@Component
public class PerfumeFollowMapper {

  public PerfumeFollow toDomain(PerfumeFollowJpaEntity perfumeFollowEntity) {
    return new PerfumeFollow(
        perfumeFollowEntity.getId(),
        perfumeFollowEntity.getUserId(),
        perfumeFollowEntity.getPerfumeId(),
        perfumeFollowEntity.getCreatedAt(),
        perfumeFollowEntity.getUpdatedAt(),
        perfumeFollowEntity.getDeletedAt());
  }
  public PerfumeFollowJpaEntity toEntity(PerfumeFollow perfumeFollow) {
    return new PerfumeFollowJpaEntity(
        perfumeFollow.getId(),
        perfumeFollow.getUserId(),
        perfumeFollow.getPerfumeId(),
        perfumeFollow.getCreatedAt(),
        perfumeFollow.getUpdatedAt(),
        perfumeFollow.getDeletedAt());
  }
}
