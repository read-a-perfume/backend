package io.perfume.api.business.adapter.out.persistence;

import io.perfume.api.base.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Comment;
import org.jetbrains.annotations.NotNull;

@Entity(name = "business")
@Table(name = "business",
    uniqueConstraints = {
        @UniqueConstraint(name = "uni_business_1", columnNames = "registrationNumber"),
    },
    indexes = {
        @Index(name = "idx_business_1", columnList = "companyLogoId"),
    })
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
public class BusinessJpaEntity extends BaseTimeEntity {

  @EqualsAndHashCode.Include
  @ToString.Include
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Comment("PK")
  private Long id;

  @NotNull
  @Column(nullable = false)
  @Comment("회사 이름")
  private String companyName;

  @NotNull
  @Column(nullable = false)
  @Comment("사업자 등록 번호")
  private String registrationNumber;

  @Comment("File Table Entity")
  private Long companyLogoId;
}
