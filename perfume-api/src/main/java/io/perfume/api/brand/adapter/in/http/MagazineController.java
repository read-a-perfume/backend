package io.perfume.api.brand.adapter.in.http;

import io.perfume.api.brand.adapter.in.http.dto.CreateMagazineRequestDto;
import io.perfume.api.brand.adapter.in.http.dto.CreateMagazineResponseDto;
import io.perfume.api.brand.application.port.in.CreateMagazineUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/{id}/magazines")
public class MagazineController {

  private final CreateMagazineUseCase createMagazineUsecase;

  @PreAuthorize("isAuthenticated()") // and hasRole('ADMIN')
  @PostMapping
  public CreateMagazineResponseDto createMagazine(
      @AuthenticationPrincipal final User user,
      @PathVariable("id") Long brandId,
      @RequestBody final CreateMagazineRequestDto requestDto) {
    final var userId = Long.parseLong(user.getUsername());
    final var now = LocalDateTime.now();
    final var response = createMagazineUsecase.create(userId, requestDto.toCommand(brandId, now));
    return new CreateMagazineResponseDto(response.id());
  }
}
