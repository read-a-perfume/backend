package io.perfume.api.file.application.port.in;

import io.perfume.api.file.application.port.in.dto.SaveFileResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

public interface FileUploadUseCase {

    SaveFileResult singleFileUpload(MultipartFile file, LocalDateTime now) throws FileNotFoundException;

    List<SaveFileResult> multiFileUpload(List<MultipartFile> files, LocalDateTime now);
}
