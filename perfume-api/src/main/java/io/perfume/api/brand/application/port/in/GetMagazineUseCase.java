package io.perfume.api.brand.application.port.in;

import dto.repository.CursorPagination;
import io.perfume.api.brand.application.port.in.dto.GetMagazineCommand;
import io.perfume.api.brand.application.port.in.dto.GetMagazineResult;

import java.util.List;

public interface GetMagazineUseCase {

    List<GetMagazineResult> getMagazines(long brandId);

    CursorPagination<GetMagazineResult> getMagazines(GetMagazineCommand command);
}
