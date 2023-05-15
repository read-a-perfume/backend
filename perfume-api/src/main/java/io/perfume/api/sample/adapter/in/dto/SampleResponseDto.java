package io.perfume.api.sample.adapter.in.dto;

import java.time.LocalDateTime;

public record SampleResponseDto (Long id, String name, LocalDateTime createdAt) {
}
