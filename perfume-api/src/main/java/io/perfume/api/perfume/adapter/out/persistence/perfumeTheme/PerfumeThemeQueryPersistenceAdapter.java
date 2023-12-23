package io.perfume.api.perfume.adapter.out.persistence.perfumeTheme;

import static io.perfume.api.perfume.adapter.out.persistence.perfumeTheme.QPerfumeThemeEntity.perfumeThemeEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.perfume.adapter.out.persistence.perfumeTheme.mapper.PerfumeThemeMapper;
import io.perfume.api.perfume.application.port.out.PerfumeThemeQueryRepository;
import io.perfume.api.perfume.domain.PerfumeTheme;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class PerfumeThemeQueryPersistenceAdapter implements PerfumeThemeQueryRepository {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public Optional<PerfumeTheme> getRecentTheme() {
    PerfumeThemeEntity entity =
        jpaQueryFactory
            .select(perfumeThemeEntity)
            .from(perfumeThemeEntity)
            .orderBy(perfumeThemeEntity.id.desc())
            .where(perfumeThemeEntity.deletedAt.isNull())
            .fetchFirst();

    return Optional.ofNullable(PerfumeThemeMapper.fromEntity(entity));
  }
}
