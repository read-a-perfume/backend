package io.perfume.api.sample.infrastructure.api.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateSampleRequestDto (@NotBlank String name) {
}
