package io.perfume.api.sample.adapter.in.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateSampleRequestDto (@NotBlank String name) {
}
