package io.perfume.api.sample.application.port;

import io.perfume.api.sample.domain.Sample;

public interface SampleRepository {

    Sample save(Sample entity);
}
