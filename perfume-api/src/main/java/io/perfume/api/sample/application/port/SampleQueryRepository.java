package io.perfume.api.sample.application.port;

import io.perfume.api.sample.domain.Sample;

import java.util.List;
import java.util.Optional;

public interface SampleQueryRepository {

    List<Sample> find();

    Optional<Sample> findById(Long id);
}
