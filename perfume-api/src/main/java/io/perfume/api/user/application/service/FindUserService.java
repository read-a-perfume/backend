package io.perfume.api.user.application.service;

import io.perfume.api.file.application.port.in.FindFileUseCase;
import io.perfume.api.file.application.port.in.dto.FileResult;
import io.perfume.api.file.domain.File;
import io.perfume.api.user.adapter.in.http.dto.FindMyDetailInformationResponseDto;
import io.perfume.api.user.application.exception.NotFoundUserException;
import io.perfume.api.user.application.port.in.FindMyDetailInformationUseCase;
import io.perfume.api.user.application.port.in.FindUserUseCase;
import io.perfume.api.user.application.port.in.dto.UserResult;
import io.perfume.api.user.application.port.out.SocialAccountQueryRepository;
import io.perfume.api.user.application.port.out.UserQueryRepository;
import io.perfume.api.user.domain.SocialAccount;
import io.perfume.api.user.domain.User;

import java.util.Optional;

import jwt.JsonWebTokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindUserService implements FindUserUseCase, FindMyDetailInformationUseCase {

  private final UserQueryRepository userQueryRepository;

  private final SocialAccountQueryRepository oauthQueryRepository;

  private final FindFileUseCase findFileUseCase;
  private final JsonWebTokenGenerator jsonWebTokenGenerator;


  @Override
  public Optional<UserResult> findOneByEmail(String email) {
    return userQueryRepository.findOneByEmail(email).map(this::toDto);
  }

  @Override
  public Optional<UserResult> findOneBySocialId(String socialId) {
    return oauthQueryRepository.findOneBySocialId(socialId).map(this::toDto);
  }

  private UserResult toDto(User user) {
    return new UserResult(user.getId(), user.getUsername(), user.getEmail(), user.getName(),
        user.getCreatedAt());
  }

  private UserResult toDto(SocialAccount socialAccount) {
    return toDto(socialAccount.getUser());
  }

  @Override
  public FindMyDetailInformationResponseDto findMyDetailInformation(String accessToken) {

    Long userId = jsonWebTokenGenerator.getClaim(accessToken, "userId", Long.class);

    User user = userQueryRepository.loadUser(userId).orElseThrow(NotFoundUserException::new);

    FileResult file = findFileUseCase.findFile(user.getThumbnailId());

    return new FindMyDetailInformationResponseDto(
            user.getUsername(),
            user.getName(),
            user.getBio(),
            file.url(),
            user.getEmail(),
            user.getBirth(),
            user.getIsOauth()
    );
  }
}
