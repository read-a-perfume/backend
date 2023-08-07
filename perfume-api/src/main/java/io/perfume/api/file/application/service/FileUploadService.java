package io.perfume.api.file.application.service;

import io.perfume.api.file.application.exception.SaveFileNotFoundException;
import io.perfume.api.file.application.port.in.FileUploadUseCase;
import io.perfume.api.file.application.port.in.dto.MultiFileResponseDto;
import io.perfume.api.file.application.port.in.dto.SaveFileResult;
import io.perfume.api.file.application.port.out.FileRepository;
import io.perfume.api.file.domain.File;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class FileUploadService implements FileUploadUseCase {

    private final FileRepository fileRepository;

    private final String cdnUrl = "";

    public FileUploadService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public SaveFileResult singleFileUpload(Long userId, MultipartFile file, LocalDateTime now) {
        String URL = cdnUrl + file.getOriginalFilename();
        if (file != null && !file.isEmpty()) {
            File saveFile = fileRepository.save(File.createFile(URL, userId, now));
            return new SaveFileResult(saveFile.getUrl(), userId, saveFile.getId(), now);
        } else {
            throw new SaveFileNotFoundException();
        }
    }

    @Override
    public MultiFileResponseDto multiFileUpload(User user, List<MultipartFile> files, LocalDateTime now) {
        List<File> saveFiles = new ArrayList<>();
        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                String URL = cdnUrl + file.getOriginalFilename();
                saveFiles.add(File.createFile(URL, Long.parseLong(user.getUsername()), now));
            }
            return toDto(fileRepository.saveAll(saveFiles), now);
        } else {
            throw new SaveFileNotFoundException();
        }
    }

    private MultiFileResponseDto toDto(List<File> saveFiles, LocalDateTime now) {
        List<SaveFileResult> results = new ArrayList<>();
        for (File file : saveFiles) {
            results.add(
                    new SaveFileResult(file.getUrl(), file.getUserId(), file.getId(), now)
            );
        }
        return new MultiFileResponseDto(results);
    }
}
