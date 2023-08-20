package io.perfume.api.user.adapter.in.http.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record SetNickNameRequestDto(
    @NotEmpty
    @NotBlank
    String name
) {
}
