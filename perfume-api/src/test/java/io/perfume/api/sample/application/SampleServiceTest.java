package io.perfume.api.sample.application;

import io.perfume.api.sample.application.dto.SampleResult;
import io.perfume.api.sample.application.exception.EntityNotFoundException;
import io.perfume.api.sample.application.port.SampleQueryRepository;
import io.perfume.api.sample.domain.Sample;
import io.perfume.api.sample.fixture.StubSampleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SampleServiceTest {

    private final StubSampleRepository stubSampleRepository = new StubSampleRepository();

    private final SampleService sampleService = new SampleService(stubSampleRepository, stubSampleRepository);

    @BeforeEach()
    void beforeEach() {
        stubSampleRepository.clear();
    }

    @Test
    void getSamples() {
        // given
        LocalDateTime now = LocalDateTime.now();
        stubSampleRepository.add(Sample.builder().id(1L).name("sample 1").createdAt(now).build());
        stubSampleRepository.add(Sample.builder().id(2L).name("sample 2").createdAt(now).build());
        stubSampleRepository.add(Sample.builder().id(3L).name("sample 3").createdAt(now).build());

        // when
        List<SampleResult> sampleResult = sampleService.getSamples();

        // then
        assertThat(sampleResult).hasSize(3);
    }

    @Test
    void getSample() {
        // given
        LocalDateTime now = LocalDateTime.now();
        stubSampleRepository.add(Sample.builder().id(1L).name("sample 1").createdAt(now).build());
        stubSampleRepository.add(Sample.builder().id(2L).name("sample 2").createdAt(now).build());
        stubSampleRepository.add(Sample.builder().id(3L).name("sample 3").createdAt(now).build());
        Long targetId = 2L;

        // when
        SampleResult sampleResult = sampleService.getSample(targetId);

        // then
        assertThat(sampleResult.id()).isEqualTo(targetId);
    }

    @Test
    void createSample() {
        // given
        String name = "sample 1";

        // when
        SampleResult sampleResult = sampleService.createSample(name);

        // then
        assertThat(stubSampleRepository.size()).isEqualTo(1);
        assertThat(sampleResult.name()).isEqualTo(name);
    }

    @Test
    void updateSample() {
        // given
        LocalDateTime now = LocalDateTime.now();
        Sample sample = Sample.builder().id(1L).name("sample 1").createdAt(now).build();
        stubSampleRepository.add(sample);

        // when
        SampleResult sampleResult = sampleService.updateSample(1L, "changed sample 1");

        // then
        assertThat(sampleResult.name()).isEqualTo("changed sample 1");
        assertThat(sample.getName()).isEqualTo("changed sample 1");
    }

    @Test
    void testUpdateSampleIfNotExists() {
        // when & then
        assertThrows(EntityNotFoundException.class, () -> sampleService.updateSample(1L, "sample 1"));
    }

    @Test
    void deleteSample() {
        // given
        LocalDateTime now = LocalDateTime.now();
        Sample sample = Sample.builder().id(1L).name("sample 1").createdAt(now).build();
        stubSampleRepository.add(sample);

        // when
        SampleResult sampleResult = sampleService.deleteSample(1L);

        // then
        assertThat(sampleResult.id()).isEqualTo(1L);
    }
}
