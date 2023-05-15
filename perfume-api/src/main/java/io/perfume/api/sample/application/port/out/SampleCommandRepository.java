package io.perfume.api.sample.application.port.out;

import io.perfume.api.sample.domain.Sample;

public interface SampleCommandRepository {

    Sample save(Sample entity);
}
