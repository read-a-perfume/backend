package io.perfume.api.sample.application.dto;

import java.time.LocalDateTime;

public record SampleResult (Long id, String name, LocalDateTime createdAt) {
}
