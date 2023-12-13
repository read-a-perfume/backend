package io.perfume.api.brand.application.port.in.dto;

import java.util.List;

public record GetMagazineResult(
    Long id, String title, String content, Long coverThumbnailId, List<String> tags) {}
