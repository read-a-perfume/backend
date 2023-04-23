package io.perfume.api.sample.infrastructure.persistence;

import io.perfume.api.sample.application.port.SampleRepository;
import io.perfume.api.sample.domain.Sample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SampleJPARepository extends JpaRepository<Sample, Long>, SampleRepository {
}
