package io.perfume.api.brand.adapter.in.http.dto;

import io.perfume.api.brand.application.port.in.dto.GetMagazineCommand;

public record GetMagazineRequestDto(Integer pageSize, String before, String after) {

  public GetMagazineCommand toCommand(long brandId) {
    return new GetMagazineCommand(getSizeOrDefault(), before, after, brandId);
  }

  private int getSizeOrDefault() {
    return pageSize == null ? 10 : pageSize;
  }
}
