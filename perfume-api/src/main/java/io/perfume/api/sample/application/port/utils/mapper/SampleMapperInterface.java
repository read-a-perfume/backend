package io.perfume.api.sample.application.port.utils.mapper;

import io.perfume.api.sample.application.port.in.dto.SampleResult;
import io.perfume.api.sample.domain.Sample;

public interface SampleMapperInterface {
    SampleResult toSampleResult(Sample sample);
}
