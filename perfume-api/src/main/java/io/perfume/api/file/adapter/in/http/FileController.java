package io.perfume.api.file.adapter.in.http;

import io.perfume.api.file.adapter.in.http.dto.UpdateFileResponseDto;
import io.perfume.api.file.application.port.in.dto.FileResult;
import io.perfume.api.file.application.service.FileService;
import java.io.IOException;
import java.time.LocalDateTime;
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
@RequestMapping("/v1/files")
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
public class FileController {

  private final FileService fileService;

  @PostMapping
  public ResponseEntity<UpdateFileResponseDto> uploadFile(
      @AuthenticationPrincipal final User user, final MultipartFile file) {
    try {
      final LocalDateTime now = LocalDateTime.now();
      final long userId = Long.parseLong(user.getUsername());
      final FileResult result = fileService.uploadFile(userId, file.getBytes(), now);

      return ResponseEntity.ok(new UpdateFileResponseDto(result.id(), result.url()));
    } catch (IOException e) {
      return ResponseEntity.internalServerError().build();
    }
  }
}
