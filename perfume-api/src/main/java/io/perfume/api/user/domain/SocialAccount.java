package io.perfume.api.user.domain;

import java.time.LocalDateTime;
import lombok.Getter;

public class SocialAccount {

  private final Long id;
  private final String identifier;
  private final String email;
  private final SocialProvider socialProvider;
  private final LocalDateTime createdAt;
  private final LocalDateTime updatedAt;
  private final LocalDateTime deletedAt;
  private User user;

  public SocialAccount(Long id, String identifier, String email, SocialProvider socialProvider,
                       User user,
                       LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
    this.id = id;
    this.identifier = identifier;
    this.email = email;
    this.socialProvider = socialProvider;
    this.user = user;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.deletedAt = deletedAt;
  }

  public void link(User user) {
    this.user = user;
  }

  public static SocialAccount createGoogleSocialAccount(
      String identifier, String email, LocalDateTime now) {

    return new SocialAccount(null, identifier, email, SocialProvider.GOOGLE, null, now, now, null);
  }

  public Long getId() {
    return id;
  }

  public Long getUserId() {
    return user.getId();
  }
}
