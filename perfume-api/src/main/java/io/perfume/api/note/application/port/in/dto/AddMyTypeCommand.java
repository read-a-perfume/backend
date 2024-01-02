package io.perfume.api.note.application.port.in.dto;

import java.util.List;

public record AddMyTypeCommand(Long userId, List<Long> categoryIds) {}
