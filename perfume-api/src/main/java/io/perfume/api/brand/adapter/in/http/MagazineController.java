package io.perfume.api.brand.adapter.in.http;

import io.perfume.api.brand.adapter.in.http.dto.CreateMagazineRequestDto;
import io.perfume.api.brand.adapter.in.http.dto.CreateMagazineResponseDto;
import io.perfume.api.brand.application.port.in.CreateMagazineUseCase;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/magazines")
public class MagazineController {

  private final CreateMagazineUseCase createMagazineUsecase;

  // TODO : hasRole 추가
  @PreAuthorize("isAuthenticated() and hasRole('ROLE_ADMIN')")
  @PostMapping
  public CreateMagazineResponseDto createMagazine(
      @AuthenticationPrincipal final User user,
      @RequestBody final CreateMagazineRequestDto requestDto) {
    final var userId = Long.parseLong(user.getUsername());
    final var now = LocalDateTime.now();
    var response = createMagazineUsecase.create(userId, requestDto.toCommand(now));
    return new CreateMagazineResponseDto(response.id());
  }
}
