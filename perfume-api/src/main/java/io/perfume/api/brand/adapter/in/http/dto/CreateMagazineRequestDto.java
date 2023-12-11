package io.perfume.api.brand.adapter.in.http.dto;

import io.perfume.api.brand.application.port.in.dto.CreateMagazineCommand;
import java.time.LocalDateTime;
import java.util.List;

public record CreateMagazineRequestDto(
    String title,
    String subTitle,
    String content,
    Long coverThumbnailId,
    Long thumbnailId,
    Long brandId,
    List<String> tags) {

  public CreateMagazineCommand toCommand(LocalDateTime now) {
    return new CreateMagazineCommand(
        title, subTitle, content, coverThumbnailId, thumbnailId, brandId, tags, now);
  }
}
