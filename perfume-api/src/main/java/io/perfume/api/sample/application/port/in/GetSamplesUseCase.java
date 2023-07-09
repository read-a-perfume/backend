package io.perfume.api.sample.application.port.in;

import io.perfume.api.sample.application.port.in.dto.SampleResult;

import java.util.List;

public interface GetSamplesUseCase {
  List<SampleResult> getSamples();
}
