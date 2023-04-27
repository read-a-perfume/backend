package io.perfume.api.sample.infrastructure.persistence;

import io.perfume.api.sample.application.port.SampleRepository;
import io.perfume.api.sample.domain.Sample;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
@EnableJpaAuditing
class SampleJPARepositoryTest {

    @Autowired
    private SampleRepository sampleRepository;

    @Test
    @DisplayName("Sample entity 를 영속화한다.")
    public void save() {
        // given
        Sample sample = Sample.builder().name("sample").build();

        // when
        Sample savedSample = sampleRepository.save(sample);

        // then
        assertThat(savedSample).isNotNull();
        assertThat(savedSample.getId()).isNotNull();
    }
}
