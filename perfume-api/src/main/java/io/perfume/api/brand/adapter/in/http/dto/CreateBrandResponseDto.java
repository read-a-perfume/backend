package io.perfume.api.brand.adapter.in.http.dto;

import jakarta.validation.constraints.NotEmpty;

public record CreateBrandResponseDto (
        @NotEmpty String name
){
}
