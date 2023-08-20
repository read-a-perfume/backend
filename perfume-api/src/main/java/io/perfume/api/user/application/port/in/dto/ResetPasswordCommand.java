package io.perfume.api.user.application.port.in.dto;

public record ResetPasswordCommand(String password, String doubleCheckPassword) {

}
