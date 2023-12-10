package io.perfume.api.user.application.port.in.dto;

import io.perfume.api.user.domain.SocialProvider;
import java.time.Instant;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public record SignUpSocialUserCommand(
    String identifier,
    String email,
    String username,
    String password,
    String name,
    SocialProvider socialProvider) {

  public static SignUpSocialUserCommand byGoogle(
      String identifier, String email, String password, String name) {
    return new SignUpSocialUserCommand(
        identifier, email, generateRandomUsername(email), password, name, SocialProvider.GOOGLE);
  }

  @NotNull
  private static Long getUnixTime() {
    return Instant.now().getEpochSecond();
  }

  @NotNull
  private static String generateRandomUsername(String email) {
    if (Objects.isNull(email) || !email.contains("@")) {
      throw new IllegalArgumentException(email + " 올바른 이메일 형식이 아닙니다.");
    }

    return email.split("@")[0] + getUnixTime();
  }
}
