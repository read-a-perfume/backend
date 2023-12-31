package io.perfume.api.brand.adapter.in.http.dto;

import io.perfume.api.brand.application.port.in.dto.GetMagazineResult;
import java.util.List;

public record GetMagazinesResponseDto(
    Long id,
    String title,
    String content,
    String coverThumbnail,
    String userThumbnail,
    List<String> tags) {

  public static GetMagazinesResponseDto from(GetMagazineResult getMagazineResult) {
    return new GetMagazinesResponseDto(
        getMagazineResult.id(),
        getMagazineResult.title(),
        getMagazineResult.content(),
        getMagazineResult.coverThumbnail(),
        getMagazineResult.userThumbnail(),
        getMagazineResult.tags());
  }
}
