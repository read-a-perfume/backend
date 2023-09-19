package io.perfume.api.perfume.adapter.out.persistence.perfume;

import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.perfume.adapter.out.persistence.perfume.mapper.PerfumeMapper;
import io.perfume.api.perfume.application.exception.PerfumeNotFoundException;
import io.perfume.api.perfume.application.port.out.PerfumeQueryRepository;
import io.perfume.api.perfume.domain.Perfume;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class PerfumeQueryPersistenceAdapter implements PerfumeQueryRepository {

    private final PerfumeJpaRepository perfumeJpaRepository;
    private final PerfumeMapper perfumeMapper;

    @Override
    public Perfume findPerfumeById(Long id) {
        PerfumeJpaEntity perfumeJpaEntity = perfumeJpaRepository.findById(id).orElseThrow(() -> new PerfumeNotFoundException(id));
        return perfumeMapper.toPerfume(perfumeJpaEntity);
    }
}
