package io.perfume.api.brand.application.port.out;

import io.perfume.api.brand.domain.MagazineTag;
import java.util.List;

public interface MagazineTagRepository {

  MagazineTag save(MagazineTag magazineTag);

  List<MagazineTag> saveAll(List<MagazineTag> magazineTags);
}
