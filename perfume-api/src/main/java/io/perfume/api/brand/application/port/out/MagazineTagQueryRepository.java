package io.perfume.api.brand.application.port.out;

import io.perfume.api.brand.domain.MagazineTag;

import java.util.List;

public interface MagazineTagQueryRepository {

    List<MagazineTag> findMagazinesTags(Long magazineId);
}
