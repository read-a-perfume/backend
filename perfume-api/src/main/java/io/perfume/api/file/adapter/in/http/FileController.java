package io.perfume.api.file.adapter.in.http;

import io.perfume.api.file.adapter.in.http.dto.UpdateFileResponseDto;
import io.perfume.api.file.application.port.in.dto.FileResult;
import io.perfume.api.file.application.service.FileService;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
public class FileController {

  private final FileService fileService;

  @PostMapping("/v1/file")
  public ResponseEntity<UpdateFileResponseDto> saveFile(
      @AuthenticationPrincipal final User user, final MultipartFile file) {
    final LocalDateTime uploadTime = LocalDateTime.now();
    final long userId = parseUserId(user);
    final FileResult result = fileService.uploadFile(getFileContent(file), userId, uploadTime);

    return ResponseEntity.ok(mapToFileResponseDto(result));
  }

  @PostMapping("/v1/files")
  public ResponseEntity<List<UpdateFileResponseDto>> saveFiles(
      @AuthenticationPrincipal final User user, final List<MultipartFile> files) {
    final LocalDateTime uploadTime = LocalDateTime.now();
    final long userId = parseUserId(user);
    final List<FileResult> results =
        fileService.uploadFiles(userId, getFileContentAsList(files), uploadTime);

    return ResponseEntity.ok(results.stream().map(this::mapToFileResponseDto).toList());
  }

  private long parseUserId(User user) {
    return Long.parseLong(user.getUsername());
  }

  private byte[] getFileContent(MultipartFile file) {
    try {
      return file.getBytes();
    } catch (IOException e) {
      return null;
    }
  }

  private List<byte[]> getFileContentAsList(List<MultipartFile> files) {
    return files.stream().map(this::getFileContent).filter(Objects::nonNull).toList();
  }

  private UpdateFileResponseDto mapToFileResponseDto(FileResult result) {
    return new UpdateFileResponseDto(result.id(), result.url());
  }
}
