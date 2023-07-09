package io.perfume.api.sample.application.port.in;

import io.perfume.api.sample.application.port.in.dto.SampleResult;

public interface CreateSampleUseCase {
  SampleResult createSample(String name);
}
