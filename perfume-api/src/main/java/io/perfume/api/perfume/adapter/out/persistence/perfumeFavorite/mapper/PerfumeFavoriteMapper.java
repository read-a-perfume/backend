package io.perfume.api.perfume.adapter.out.persistence.perfumeFavorite.mapper;

import io.perfume.api.perfume.adapter.out.persistence.perfumeFavorite.PerfumeFavoriteJpaEntity;
import io.perfume.api.perfume.domain.PerfumeFavorite;
import org.springframework.stereotype.Component;

@Component
public class PerfumeFavoriteMapper {

  public PerfumeFavorite toDomain(PerfumeFavoriteJpaEntity perfumeFollowEntity) {
    return new PerfumeFavorite(
        perfumeFollowEntity.getId().getUserId(),
        perfumeFollowEntity.getId().getPerfumeId(),
        perfumeFollowEntity.getCreatedAt(),
        perfumeFollowEntity.getUpdatedAt(),
        perfumeFollowEntity.getDeletedAt());
  }

  public PerfumeFavoriteJpaEntity toEntity(PerfumeFavorite perfumeFavorite) {
    return new PerfumeFavoriteJpaEntity(
        perfumeFavorite.getUserId(),
        perfumeFavorite.getPerfumeId(),
        perfumeFavorite.getCreatedAt(),
        perfumeFavorite.getUpdatedAt(),
        perfumeFavorite.getDeletedAt());
  }
}
