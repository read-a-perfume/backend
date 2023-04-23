package io.perfume.api.sample.application;

import io.perfume.api.sample.application.dto.SampleResult;
import io.perfume.api.sample.application.exception.AccessDeniedException;
import io.perfume.api.sample.application.exception.EntityNotFoundException;
import io.perfume.api.sample.application.port.SampleQueryRepository;
import io.perfume.api.sample.application.port.SampleRepository;
import io.perfume.api.sample.domain.Sample;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SampleService {

    private final SampleRepository sampleRepository;
    private final SampleQueryRepository sampleQueryRepository;

    @Transactional(readOnly = true)
    public List<SampleResult> getSamples() {
        return sampleQueryRepository.find().stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public SampleResult getSample(Long id) {
        Sample sample = sampleQueryRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        return toDto(sample);
    }

    @Transactional
    public SampleResult createSample(String name) {
        Sample newSample = Sample.builder().name(name).build();

        return toDto(sampleRepository.save(newSample));
    }

    @Transactional
    public SampleResult updateSample(Long id, String name) {
        Sample sample = sampleQueryRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        sample.changeName(name);

        return toDto(sample);
    }

    @Transactional
    public SampleResult deleteSample(Long id) {
        Sample sample = sampleQueryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        LocalDateTime now = LocalDateTime.now();

        sample.delete(now);

        return toDto(sample);
    }

    private SampleResult toDto(Sample sample) {
        return new SampleResult(sample.getId(), sample.getName());
    }
}
