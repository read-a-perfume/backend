package io.perfume.api.brand.application.port.in.dto;

import java.time.LocalDateTime;
import java.util.List;

public record CreateMagazineCommand(
    String title,
    String subTitle,
    String content,
    Long coverThumbnailId,
    Long thumbnailId,
    Long brandId,
    List<String> tags,
    LocalDateTime now) {}
