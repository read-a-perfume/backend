package io.perfume.api.user.application.port.in;

public interface SendResetPasswordMailUseCase {
    void sendNewPasswordToEmail(String email, String id);
}
