package io.perfume.api.user.adapter.in.http;

import io.perfume.api.user.adapter.in.http.dto.AddUserTypeRequestDto;
import io.perfume.api.user.adapter.in.http.dto.UserTypeResponseDto;
import io.perfume.api.user.application.port.in.UserTypeUseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserTypeController {

  private final UserTypeUseCase userTypeUseCase;

  @GetMapping("/{id}/types")
  public List<UserTypeResponseDto> getTypes(@PathVariable Long id) {
    return userTypeUseCase.getUserTypes(id).stream().map(UserTypeResponseDto::from).toList();
  }

  @PostMapping("/types")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<Object> addUserTypes(
      @AuthenticationPrincipal User user, @RequestBody AddUserTypeRequestDto dto) {
    userTypeUseCase.addUserTypes(Long.parseLong(user.getUsername()), dto.categoryIds());

    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).build();
  }
}
