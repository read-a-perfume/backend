package io.perfume.api.user.adapter.in.http;

import io.perfume.api.user.adapter.in.http.dto.CreateUserTypeRequestDto;
import io.perfume.api.user.adapter.in.http.dto.UserTypeResponseDto;
import io.perfume.api.user.application.port.in.UserTypeUseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/v1/user/types")
@RequiredArgsConstructor
public class UserTypeController {

  private final UserTypeUseCase userTypeUseCase;

  @GetMapping
  @PreAuthorize("isAuthenticated()")
  public List<UserTypeResponseDto> getTypes() {
    UserDetails authentication =
        (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    return userTypeUseCase.getUserTypes(Long.parseLong(authentication.getUsername())).stream()
        .map(UserTypeResponseDto::from)
        .toList();
  }

  @PostMapping
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<Object> createType(@RequestBody CreateUserTypeRequestDto dto) {
    UserDetails authentication =
        (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    userTypeUseCase.createUserType(Long.parseLong(authentication.getUsername()), dto.noteId());

    return ResponseEntity.status(HttpStatus.CREATED)
        .contentType(MediaType.APPLICATION_JSON)
        .build();
  }
}
