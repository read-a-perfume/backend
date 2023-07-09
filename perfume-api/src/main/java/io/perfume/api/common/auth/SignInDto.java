package io.perfume.api.common.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SignInDto {
  @NotBlank
  private String username;

  @NotBlank
  private String password;

  @Builder
  private SignInDto(String username, String password) {
    this.username = username;
    this.password = password;
  }
}
