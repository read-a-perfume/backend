package io.perfume.api.auth.application.port.in;

@FunctionalInterface
public interface MakeNewAccessTokenUseCase {
    String makeNewAccessToken(String accessToken);
}
