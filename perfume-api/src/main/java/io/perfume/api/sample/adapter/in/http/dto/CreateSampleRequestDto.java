package io.perfume.api.sample.adapter.in.http.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateSampleRequestDto(@NotBlank String name) {
}
