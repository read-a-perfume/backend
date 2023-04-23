package io.perfume.api.sample.infrastructure.persistence;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.perfume.api.sample.application.port.SampleQueryRepository;
import io.perfume.api.sample.domain.Sample;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static io.perfume.api.sample.domain.QSample.sample;

@Repository
@RequiredArgsConstructor
public class SampleQueryRepositoryImpl implements SampleQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Sample> find() {
        return jpaQueryFactory.selectFrom(sample).fetch();
    }

    @Override
    public Optional<Sample> findById(Long id) {
        Sample savedSample =
                jpaQueryFactory
                        .selectFrom(sample)
                        .where(sample.id.eq(id))
                        .fetchOne();

        return Optional.ofNullable(savedSample);
    }
}
