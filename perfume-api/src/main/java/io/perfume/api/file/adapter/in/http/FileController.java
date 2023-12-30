package io.perfume.api.file.adapter.in.http;

import io.perfume.api.file.adapter.in.http.dto.UpdateFileResponseDto;
import io.perfume.api.file.application.port.in.dto.FileResult;
import io.perfume.api.file.application.service.UploadFileService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
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

  private final UploadFileService uploadFileService;

  @PostMapping("/v1/file")
  public UpdateFileResponseDto saveFile(
      @AuthenticationPrincipal final User user, final MultipartFile file) {
    final LocalDateTime uploadTime = LocalDateTime.now();
    final long userId = parseUserId(user);
    final FileResult result = uploadFileService.uploadFile(file, userId, uploadTime);

    return UpdateFileResponseDto.from(result);
  }

  @PostMapping("/v1/files")
  public List<UpdateFileResponseDto> saveFiles(
      @AuthenticationPrincipal final User user, final List<MultipartFile> files) {
    final LocalDateTime uploadTime = LocalDateTime.now();
    final long userId = parseUserId(user);
    final List<FileResult> results = uploadFileService.uploadFiles(files, userId, uploadTime);

    return results.stream().map(UpdateFileResponseDto::from).toList();
  }

  private long parseUserId(User user) {
    return Long.parseLong(user.getUsername());
  }
}
