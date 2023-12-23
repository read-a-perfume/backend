package io.perfume.api.perfume.application.port.in.dto;

import java.util.List;

public record PerfumeThemeResult(
    String title, String content, String thumbnail, List<SimplePerfumeResult> perfumes) {}
