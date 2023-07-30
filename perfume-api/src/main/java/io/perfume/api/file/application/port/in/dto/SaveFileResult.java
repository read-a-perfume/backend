package io.perfume.api.file.application.port.in.dto;

import java.time.LocalDateTime;

public record SaveFileResult(String url, LocalDateTime now) {
}
