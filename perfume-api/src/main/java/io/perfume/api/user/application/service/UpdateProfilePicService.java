package io.perfume.api.user.application.service;

import io.perfume.api.file.application.port.in.dto.FileResult;
import io.perfume.api.file.application.service.UploadFileService;
import io.perfume.api.user.application.exception.UserNotFoundException;
import io.perfume.api.user.application.port.in.UpdateProfilePicUseCase;
import io.perfume.api.user.application.port.in.dto.UpdateProfilePicResult;
import io.perfume.api.user.application.port.out.UserQueryRepository;
import io.perfume.api.user.application.port.out.UserRepository;
import io.perfume.api.user.domain.User;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class UpdateProfilePicService implements UpdateProfilePicUseCase {

  private final UserQueryRepository userQueryRepository;
  private final UserRepository userRepository;
  private final UploadFileService fileUploadService;

  public UpdateProfilePicService(
      UserQueryRepository userQueryRepository,
      UserRepository userRepository,
      UploadFileService fileUploadService) {
    this.userQueryRepository = userQueryRepository;
    this.userRepository = userRepository;
    this.fileUploadService = fileUploadService;
  }

  @Override
  @Transactional
  public UpdateProfilePicResult update(Long userId, MultipartFile file, LocalDateTime now) {
    FileResult fileResult = fileUploadService.uploadFile(file, userId, now);
    User user =
        userQueryRepository
            .findUserById(userId)
            .orElseThrow(() -> new UserNotFoundException(userId));
    user.updateThumbnailId(fileResult.id());
    userRepository.save(user);
    return UpdateProfilePicResult.from(userId, fileResult);
  }
}
