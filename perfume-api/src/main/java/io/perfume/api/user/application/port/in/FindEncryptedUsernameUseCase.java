package io.perfume.api.user.application.port.in;

public interface FindEncryptedUsernameUseCase {
  String getCensoredUsername(String email);
}
