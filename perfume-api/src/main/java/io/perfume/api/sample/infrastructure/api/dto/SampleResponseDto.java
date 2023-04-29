package io.perfume.api.sample.infrastructure.api.dto;

import java.time.LocalDateTime;

public record SampleResponseDto (Long id, String name, LocalDateTime createdAt) {
}
