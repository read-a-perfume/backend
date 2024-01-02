package io.perfume.api.user.adapter.in.http.dto;

import java.util.List;

public record CreateUserTypeRequestDto(List<Long> categoryIds) {}
