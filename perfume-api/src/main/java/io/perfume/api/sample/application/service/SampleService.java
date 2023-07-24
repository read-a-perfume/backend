package io.perfume.api.sample.application.service;

import io.perfume.api.sample.application.exception.SampleNotFoundException;
import io.perfume.api.sample.application.port.in.CreateSampleUseCase;
import io.perfume.api.sample.application.port.in.DeleteSampleUseCase;
import io.perfume.api.sample.application.port.in.GetSampleUseCase;
import io.perfume.api.sample.application.port.in.GetSamplesUseCase;
import io.perfume.api.sample.application.port.in.UpdateSampleUseCase;
import io.perfume.api.sample.application.port.in.dto.SampleResult;
import io.perfume.api.sample.application.port.out.SampleCommandRepository;
import io.perfume.api.sample.application.port.out.SampleQueryRepository;
import io.perfume.api.sample.application.port.utils.mapper.SampleMapperInterface;
import io.perfume.api.sample.domain.Sample;
import java.util.List;

import io.perfume.api.sample.adapter.utils.SampleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SampleService implements CreateSampleUseCase, DeleteSampleUseCase,
    GetSamplesUseCase, GetSampleUseCase, UpdateSampleUseCase {

  private final SampleCommandRepository sampleRepository;
  private final SampleQueryRepository sampleQueryRepository;

  private final SampleMapperInterface sampleMapperInterface;
  @Override
  @Transactional(readOnly = true)
  public List<SampleResult> getSamples() {
    return sampleQueryRepository.find().stream().map(this::toDto).toList();
  }

  @Override
  @Transactional(readOnly = true)
  public SampleResult getSample(Long id) {
    Sample sample = sampleQueryRepository.findById(id).orElseThrow(SampleNotFoundException::new);

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
    Sample sample = sampleQueryRepository.findById(id).orElseThrow(SampleNotFoundException::new);

    sample.changeName(name);

    return toDto(sample);
  }

  @Override
  public SampleResult deleteSample(Long id) {
    return null;
  }

  private SampleResult toDto(Sample sample) {
    return sampleMapperInterface.toSampleResult(sample);
  }
}
