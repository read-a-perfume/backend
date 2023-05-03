package io.perfume.api.sample.domain;

import io.perfume.api.base.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
public class User extends BaseTimeEntity {
    // NotNull : ""허용
    // NotEmpty : " " 허용
    // NotBlank : " " 허용 x

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull
    private String username;

    @NotNull
    @Email      // 제약조건 설정?  --> @Email "" 는 통과
    @Column(unique = true, updatable = false)
    private String email;

    @NotBlank(message = "공백(스페이스 바)을 허용하지 않습니다.")
    private String password;

    @NotEmpty
    @Column(updatable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ROLE role;

    private User(String username, String email, String password, String name, ROLE role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    // todo: 참조 엔티 file, business 당장에 작업 불가
    // todo: length 정책

    public enum ROLE {
        USER, BUSINESS
    }

    public static User generalUserJoin(String username, String email, String password, String name, ROLE role) {
        return new User(username, email, password, name, role);
    }
}