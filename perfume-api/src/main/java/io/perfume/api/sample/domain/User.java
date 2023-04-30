package io.perfume.api.sample.domain;

import io.perfume.api.base.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class User extends BaseTimeEntity {
    // NotNull : ""허용
    // NotEmpty : " " 허용
    // NotBlank : " " 허용 x

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private ROLE role;

    // todo: 참조 엔티 file, business 당장에 작업 불가
    // todo: length 정책

    public enum ROLE {
        USER, BUSINESS
    }

    @Builder
    public User(Long id, String username, String email, String password, String name, ROLE role,
                @NotNull LocalDateTime createAt, @NotNull LocalDateTime updateAt) {
        super(id, createAt, updateAt);
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }
}
