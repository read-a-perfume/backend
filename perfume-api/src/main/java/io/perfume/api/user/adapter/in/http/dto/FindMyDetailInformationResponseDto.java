package io.perfume.api.user.adapter.in.http.dto;

import java.time.LocalDate;

public record FindMyDetailInformationResponseDto(
    String username,
    String name,
    String bio,
    String picture,
    String email,
    LocalDate birth,
    Boolean isOauth
) {

}