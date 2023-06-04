package io.perfume.api.common.filters.login;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class LoginDto {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
