package io.perfume.api.user.domain;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class SocialAccount {

  private final Long id;
  private final String identifier;
  private final SocialProvider socialProvider;
  private final LocalDateTime createdAt;
  private final LocalDateTime updatedAt;
  private final LocalDateTime deletedAt;
  private User user;

  public SocialAccount(Long id, String identifier, SocialProvider socialProvider,
                       User user,
                       LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
    this.id = id;
    this.identifier = identifier;
    this.socialProvider = socialProvider;
    this.user = user;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.deletedAt = deletedAt;
  }

  public static SocialAccount createGoogleSocialAccount(
      String identifier, LocalDateTime now) {

    return new SocialAccount(null, identifier, SocialProvider.GOOGLE, null, now, now, null);
  }

  public void connect(User user) {
    this.user = user;
  }
}
