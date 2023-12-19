package io.perfume.api.user.application.service.dto;

public record UpdateUserPasswordCommand(String currentPassword, String newPassword) {}
