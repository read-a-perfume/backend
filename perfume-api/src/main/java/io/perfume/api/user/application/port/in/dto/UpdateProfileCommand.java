package io.perfume.api.user.application.port.in.dto;

import io.perfume.api.user.adapter.out.persistence.user.Sex;
import java.time.LocalDate;

public record UpdateProfileCommand(Long userId, String bio, LocalDate birthday, Sex sex) {}
