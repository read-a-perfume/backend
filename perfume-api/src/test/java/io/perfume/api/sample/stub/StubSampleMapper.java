package io.perfume.api.sample.stub;

import io.perfume.api.sample.application.port.in.dto.SampleResult;
import io.perfume.api.sample.application.port.utils.mapper.SampleMapperInterface;
import io.perfume.api.sample.domain.Sample;

public class StubSampleMapper implements SampleMapperInterface {
    @Override
    public SampleResult toSampleResult(Sample sample) {
        return null;
    }
}
