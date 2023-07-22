package io.perfume.api.file.application.port.in;

import io.perfume.api.file.application.port.in.dto.FileResult;
import io.perfume.api.file.domain.File;

public interface FindFileUseCase {

    /**
     * File을 1개 조회한다.
     *
     * @param fileId file pk
     * @return fileDTO
     */
    FileResult findFile(Long fileId);
}
