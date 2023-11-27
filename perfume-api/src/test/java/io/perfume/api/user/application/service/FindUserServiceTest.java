package io.perfume.api.user.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import io.perfume.api.file.application.port.in.FindFileUseCase;
import io.perfume.api.file.domain.File;
import io.perfume.api.user.application.port.in.dto.UserProfileResult;
import io.perfume.api.user.application.port.out.UserQueryRepository;
import io.perfume.api.user.domain.User;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FindUserServiceTest {
  @InjectMocks
  private FindUserService findUserService;
  @Mock
  private UserQueryRepository userQueryRepository;
  @Mock
  private FindFileUseCase findFileUseCase;

  @Test
  @DisplayName("유저 아이디로 이름, 프로필 사진을 조회할 수 있다.")
  void findUserProfileById() {
    // given
    Long userId = 1L;
    User user = User.builder()
        .id(userId)
        .username("username")
        .thumbnailId(1L).build();
    File file = File.builder().id(1L).url("thumbnail.com").userId(userId).build();

    given(userQueryRepository.findUserById(anyLong())).willReturn(Optional.ofNullable(user));
    given(findFileUseCase.findFileById(anyLong())).willReturn(Optional.ofNullable(file));

    // when
    UserProfileResult userProfile = findUserService.findUserProfileById(userId);

    // then
    assertEquals(userId, userProfile.userId());
    assertEquals(user.getUsername(), userProfile.username());
    assertEquals(file.getUrl(), userProfile.thumbnail());
  }
}
