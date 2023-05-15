package io.perfume.api.sample.infrastructure.persistence;

import io.perfume.api.configuration.TestQueryDSLConfiguration;
import io.perfume.api.sample.adapter.out.persistence.SampleQueryRepositoryImpl;
import io.perfume.api.sample.domain.Sample;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@Import(TestQueryDSLConfiguration.class)
@DataJpaTest
@EnableJpaAuditing
class SampleQueryRepositoryImplTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private SampleQueryRepositoryImpl sampleQueryRepository;

    @Test
    void find() {
        // given
        entityManager.persist(Sample.builder().name("sample").build());
        entityManager.persist(Sample.builder().name("sample").build());
        entityManager.flush();
        entityManager.clear();

        // when
        List<Sample> actual = sampleQueryRepository.find();

        // then
        assertThat(actual).hasSize(2);
    }

    @Test
    void findById() {
        // given
        entityManager.persist(Sample.builder().name("sample").build());
        entityManager.flush();
        entityManager.clear();

        // when
        Optional<Sample> actual = sampleQueryRepository.findById(1L);

        // then
        assertThat(actual.isPresent()).isTrue();
    }
}
