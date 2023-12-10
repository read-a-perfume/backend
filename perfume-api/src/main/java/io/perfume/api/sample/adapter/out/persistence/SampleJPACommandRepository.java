package io.perfume.api.sample.adapter.out.persistence;

import io.perfume.api.sample.application.port.out.SampleCommandRepository;
import io.perfume.api.sample.domain.Sample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SampleJPACommandRepository
    extends JpaRepository<Sample, Long>, SampleCommandRepository {}
