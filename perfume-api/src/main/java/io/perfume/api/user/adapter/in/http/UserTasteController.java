package io.perfume.api.user.adapter.in.http;

import io.perfume.api.user.adapter.in.http.dto.CreateUserTasteRequestDto;
import io.perfume.api.user.adapter.in.http.dto.GetUserTasteResponseDto;
import io.perfume.api.user.application.port.in.CreateUserTasteUseCase;
import io.perfume.api.user.application.port.in.FindUserTasteUseCase;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user/tastes")
public class UserTasteController {

  private final FindUserTasteUseCase findUserTasteUseCase;

  private final CreateUserTasteUseCase createUserTasteUseCase;

  public UserTasteController(
      FindUserTasteUseCase findUserTasteUseCase, CreateUserTasteUseCase createUserTasteUseCase) {
    this.findUserTasteUseCase = findUserTasteUseCase;
    this.createUserTasteUseCase = createUserTasteUseCase;
  }

  @GetMapping
  @PreAuthorize("isAuthenticated()")
  public List<GetUserTasteResponseDto> getTastes() {
    UserDetails authentication =
        (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    return findUserTasteUseCase.getUserTastes(Long.parseLong(authentication.getUsername())).stream()
        .map(GetUserTasteResponseDto::from)
        .toList();
  }

  @PostMapping
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<Object> createTaste(@RequestBody CreateUserTasteRequestDto dto) {
    UserDetails authentication =
        (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    createUserTasteUseCase.createUserTaste(
        Long.parseLong(authentication.getUsername()), dto.noteId());

    return ResponseEntity.status(HttpStatus.CREATED)
        .contentType(MediaType.APPLICATION_JSON)
        .build();
  }
}
