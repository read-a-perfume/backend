package io.perfume.api.file.application.service;

import io.perfume.api.file.application.port.in.FindFileUseCase;
import io.perfume.api.file.application.port.out.FileQueryRepository;
import io.perfume.api.file.domain.File;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FindFileService implements FindFileUseCase {
    private final FileQueryRepository fileQueryRepository;
    @Override
    public Optional<File> findFileById(Long id) {
        return fileQueryRepository.findOneByFileId(id);
    }
}
