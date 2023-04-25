package io.perfume.api.sample.fixture;

import io.perfume.api.sample.application.port.SampleQueryRepository;
import io.perfume.api.sample.application.port.SampleRepository;
import io.perfume.api.sample.domain.Sample;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class StubSampleRepository implements SampleRepository, SampleQueryRepository {

    private Set<Sample> memoryDatabase = new HashSet<>();

    @Override
    public List<Sample> find() {
        return this.memoryDatabase.stream().toList();
    }

    @Override
    public Optional<Sample> findById(Long id) {
        return memoryDatabase.stream().filter(sample -> sample.getId().equals(id)).findFirst();
    }

    @Override
    public Sample save(Sample entity) {
        this.memoryDatabase.add(entity);

        return entity;
    }

    public void add(Sample sample) {
        this.memoryDatabase.add(sample);
    }

    public int size() {
        return this.memoryDatabase.size();
    }

    public void clear() {
        this.memoryDatabase = new HashSet<>();
    }
}
