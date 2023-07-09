package io.perfume.api.sample.adapter.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import io.perfume.api.sample.application.port.out.SampleCommandRepository;
import io.perfume.api.sample.domain.Sample;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
@EnableJpaAuditing
class SampleJPARepositoryTest {

  @Autowired
  private SampleCommandRepository sampleRepository;

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
