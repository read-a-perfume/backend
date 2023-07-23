package com.알쓰.week5;

import io.perfume.api.user.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SetUserProfileController {

    private final SetUserProfileUseCase setUserProfileUseCase;

    public SetUserProfileController(SetUserProfileUseCase setUserProfileUseCase) {
        this.setUserProfileUseCase = setUserProfileUseCase;
    }

    @PutMapping("/testPut")
    public ResponseEntity<Object> setPicture(
            @AuthenticationPrincipal User user,
            String name
            ) {
            setUserProfileUseCase.setNickName(user, name);
            return ResponseEntity.ok().build();
    }
}
