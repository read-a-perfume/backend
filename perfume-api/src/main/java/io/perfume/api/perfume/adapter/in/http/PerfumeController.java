package io.perfume.api.perfume.adapter.in.http;

import io.perfume.api.perfume.adapter.in.http.dto.CreatePerfumeRequestDto;
import io.perfume.api.perfume.adapter.in.http.dto.PerfumeResponseDto;
import io.perfume.api.perfume.application.port.in.CreatePerfumeUseCase;
import io.perfume.api.perfume.application.port.in.FindPerfumeUseCase;
import io.perfume.api.perfume.application.port.in.UserFollowPerfumeUseCase;
import io.perfume.api.perfume.application.port.in.UserUnFollowPerfumeUseCase;
import io.perfume.api.perfume.application.port.in.dto.CreatePerfumeCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/perfumes")
@RequiredArgsConstructor
public class PerfumeController {

  private final FindPerfumeUseCase findPerfumeUseCase;

  private final CreatePerfumeUseCase createPerfumeUseCase;

  private final UserFollowPerfumeUseCase userFollowPerfumeUseCase;

  private final UserUnFollowPerfumeUseCase userUnFollowPerfumeUseCase;

  @GetMapping("/{id}")
  public PerfumeResponseDto findPerfumeById(@PathVariable Long id) {
    return PerfumeResponseDto.of(findPerfumeUseCase.findPerfumeById(id));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createPerfume(@RequestBody CreatePerfumeRequestDto createPerfumeRequestDto) {
    CreatePerfumeCommand createPerfumeCommand = createPerfumeRequestDto.toCommand();

    createPerfumeUseCase.createPerfume(createPerfumeCommand);
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping
  public void followPerfume(@AuthenticationPrincipal User user, @PathVariable Long perfumeId) {
    var userId = Long.parseLong(user.getUsername());
    userFollowPerfumeUseCase.followPerfume(userId, perfumeId);
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping
  public void unFollowPerfume(@AuthenticationPrincipal User user, @PathVariable Long perfumeId) {
    var userId = Long.parseLong(user.getUsername());
    userUnFollowPerfumeUseCase.unFollowPerfume(userId, perfumeId);
  }
}
