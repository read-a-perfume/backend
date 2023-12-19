package io.perfume.api.user.application.port.in;

public interface SendResetPasswordMailUseCase {

  void sendPasswordResetEmail(String email);
}
