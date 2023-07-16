package io.perfume.api.user.adapter.out.persistence.oauth;

import io.perfume.api.base.BaseTimeEntity;
import io.perfume.api.user.adapter.out.persistence.user.UserJpaEntity;
import io.perfume.api.user.domain.SocialProvider;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "social_account")
@Table(
    uniqueConstraints = {
        @UniqueConstraint(name = "uni_identifier", columnNames = "identifier")
    }
)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class SocialAccountJpaEntity extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  @ToString.Include
  Long id;

  @NotNull
  @Column(updatable = false)
  String identifier;

  @NotNull
  @Email
  @Column(updatable = false)
  String email;

  @NotNull
  @Column(updatable = false)
  @Enumerated(EnumType.STRING)
  SocialProvider socialProvider;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  UserJpaEntity user;

  public SocialAccountJpaEntity(Long id, String identifier, String email,
                                SocialProvider socialProvider,
                                UserJpaEntity user, LocalDateTime createdAt,
                                LocalDateTime updatedAt,
                                LocalDateTime deletedAt) {
    super(createdAt, updatedAt, deletedAt);

    this.id = id;
    this.identifier = identifier;
    this.email = email;
    this.socialProvider = socialProvider;
    this.user = user;
  }
}
