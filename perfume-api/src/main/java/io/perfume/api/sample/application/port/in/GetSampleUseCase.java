package io.perfume.api.sample.application.port.in;

import io.perfume.api.sample.application.port.in.dto.SampleResult;

public interface GetSampleUseCase {
    SampleResult getSample(Long id);
}
