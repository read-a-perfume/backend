package io.perfume.api.brand.adapter.in.http;

import dto.ui.CursorResponse;
import io.perfume.api.brand.adapter.in.http.dto.CreateMagazineRequestDto;
import io.perfume.api.brand.adapter.in.http.dto.CreateMagazineResponseDto;
import io.perfume.api.brand.adapter.in.http.dto.GetMagazineRequestDto;
import io.perfume.api.brand.adapter.in.http.dto.GetMagazinesResponseDto;
import io.perfume.api.brand.application.port.in.CreateMagazineUseCase;
import io.perfume.api.brand.application.port.in.GetMagazineUseCase;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/{id}/magazines")
public class MagazineController {

  private final CreateMagazineUseCase createMagazineUsecase;

  private final GetMagazineUseCase getMagazineUseCase;

  // TODO : Test, restDocs 작성
  @PreAuthorize("isAuthenticated()")
  @GetMapping
  public ResponseEntity<CursorResponse<GetMagazinesResponseDto, Long>> getMagazines(
      @PathVariable("id") Long brandId, GetMagazineRequestDto request) {
    final var magazines = getMagazineUseCase.getMagazines(request.toCommand(brandId));
    final var responseItems =
        magazines.getItems().stream().map(GetMagazinesResponseDto::from).toList();
    return ResponseEntity.ok(
        CursorResponse.of(
            responseItems,
            magazines.hasNext(),
            magazines.hasPrevious(),
            magazines.getFirstCursor().id(),
            magazines.getLastCursor().id()));
  }

  @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
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
