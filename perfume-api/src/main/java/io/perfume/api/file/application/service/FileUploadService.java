package io.perfume.api.file.application.service;

import io.perfume.api.file.application.exception.FileNotFoundException;
import io.perfume.api.file.application.port.in.FileUploadUseCase;
import io.perfume.api.file.application.port.in.dto.SaveFileResult;
import io.perfume.api.file.application.port.out.FileRepository;
import io.perfume.api.file.domain.File;
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
    public SaveFileResult singleFileUpload(MultipartFile file, LocalDateTime now) {
        String URL = cdnUrl + file.getOriginalFilename();
        File saveFile = fileRepository.save(File.createFile(URL, now))
                .orElseThrow(FileNotFoundException::new);
        return new SaveFileResult(saveFile.getUrl(), now);
    }

    @Override
    public List<SaveFileResult> multiFileUpload(List<MultipartFile> files, LocalDateTime now) {
        List<File> saveFiles = new ArrayList<>();
        if (files != null) {
            for (MultipartFile file : files) {
                String URL = cdnUrl + file.getOriginalFilename();
                saveFiles.add(File.createFile(URL, now));
            }
            fileRepository.saveAll(saveFiles);
        }

        return toDto(saveFiles, now);
    }

    private List<SaveFileResult> toDto(List<File> saveFiles, LocalDateTime now) {
        List<SaveFileResult> results = new ArrayList<>();
        for (File file : saveFiles) {
            results.add(
                    new SaveFileResult(file.getUrl(), now)
            );
        }
        return results;
    }
}
