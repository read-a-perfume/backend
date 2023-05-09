package io.perfume.api.user.domain;

import io.perfume.api.base.BaseTimeDomain;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
public class User extends BaseTimeDomain {

    // todo 상태가 더 많아지면 VO로 캡슐화 해도 좋지 않을까?
    private Long id;
    private String username;
    private String email;
    private String password;
    private String name;
    private Role role;
    private Long businessId;
    private Long thumbnailId;

    
    // 기업 사용자가 아닌 경우 회원가입시에 사용됩니다.
    public static User generalUserJoin(String username, String email, String password, String name, Role role) {
        return User.builder()
                .username(username)
                .email(email)
                .password(password)
                .name(name)
                .role(role)
                .build();
    }

    // Only Adapter
    public static User withId(Long id, String username, String email, String password, String name, Role role, Long businessId, Long thumbnailId) {
        return User.builder()
                .id(id)
                .username(username)
                .email(email)
                .password(password)
                .name(name)
                .role(role)
                .businessId(businessId)
                .thumbnailId(thumbnailId)
                .build();
    }

    @Builder
    private User(Long id, String username, String email, String password, String name, Role role, Long businessId, Long thumbnailId, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        super(createdAt, updatedAt, deletedAt);
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
        this.businessId = businessId;
        this.thumbnailId = thumbnailId;
    }
}
