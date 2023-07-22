package io.perfume.api.file.application.service;

import io.perfume.api.file.application.exception.NotFoundFileException;
import io.perfume.api.file.application.port.in.FindFileUseCase;
import io.perfume.api.file.application.port.in.dto.FileResult;
import io.perfume.api.file.application.port.out.FileQueryRepository;
import io.perfume.api.file.domain.File;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileService implements FindFileUseCase {

    private final FileQueryRepository fileQueryRepository;

    @Override
    public FileResult findFile(Long fileId) {
        File file = fileQueryRepository.loadFile(fileId).orElseThrow(NotFoundFileException::new);
        return toDto(file);
    }

    private FileResult toDto(File file) {
        return new FileResult(file.getId(), file.getUrl());
    }
}
