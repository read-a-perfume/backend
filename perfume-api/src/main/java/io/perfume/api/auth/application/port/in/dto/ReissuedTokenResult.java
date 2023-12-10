package io.perfume.api.auth.application.port.in.dto;

public record ReissuedTokenResult(String accessToken, String refreshToken) {}
