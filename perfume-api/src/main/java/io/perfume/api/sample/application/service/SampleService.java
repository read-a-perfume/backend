package io.perfume.api.sample.application.service;

import io.perfume.api.sample.application.exception.UserNotFoundException;
import io.perfume.api.sample.application.port.in.*;
import io.perfume.api.sample.application.port.in.dto.SampleResult;
import io.perfume.api.sample.application.port.out.SampleCommandRepository;
import io.perfume.api.sample.application.port.out.SampleQueryRepository;
import io.perfume.api.sample.domain.Sample;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SampleService implements CreateSampleUseCase, DeleteSampleUseCase,
        GetSamplesUseCase, GetSampleUseCase, UpdateSampleUseCase {

    private final SampleCommandRepository sampleRepository;
    private final SampleQueryRepository sampleQueryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<SampleResult> getSamples() {
        return sampleQueryRepository.find().stream().map(this::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SampleResult getSample(Long id) {
        Sample sample = sampleQueryRepository.findById(id).orElseThrow(UserNotFoundException::new);

        return toDto(sample);
    }

    @Override
    @Transactional
    public SampleResult createSample(String name) {
        Sample newSample = Sample.builder().name(name).build();

        return toDto(sampleRepository.save(newSample));
    }

    @Override
    @Transactional
    public SampleResult updateSample(Long id, String name) {
        Sample sample = sampleQueryRepository.findById(id).orElseThrow(UserNotFoundException::new);

        sample.changeName(name);

        return toDto(sample);
    }

    @Override
    public SampleResult deleteSample(Long id) {
        return null;
    }

    private SampleResult toDto(Sample sample) {
        return new SampleResult(sample.getId(), sample.getName(), sample.getCreatedAt());
    }
}
