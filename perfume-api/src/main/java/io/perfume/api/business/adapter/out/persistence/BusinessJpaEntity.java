package io.perfume.api.business.adapter.out.persistence;

import io.perfume.api.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
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
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String companyName;

    @NotNull
    @Column(nullable = false)
    private String registrationNumber;

    private Long companyLogoId;
}
