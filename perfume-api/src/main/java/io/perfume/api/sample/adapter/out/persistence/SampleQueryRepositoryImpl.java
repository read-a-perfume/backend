package io.perfume.api.sample.adapter.out.persistence;

import static io.perfume.api.sample.domain.QSample.sample;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.perfume.api.sample.application.port.out.SampleQueryRepository;
import io.perfume.api.sample.domain.Sample;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SampleQueryRepositoryImpl implements SampleQueryRepository {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<Sample> find() {
    return jpaQueryFactory.selectFrom(sample).where(sample.deletedAt.isNull()).fetch();
  }

  @Override
  public Optional<Sample> findById(Long id) {
    Sample savedSample =
        jpaQueryFactory
            .selectFrom(sample)
            .where(sample.id.eq(id).and(sample.deletedAt.isNull()))
            .fetchOne();

    return Optional.ofNullable(savedSample);
  }
}
