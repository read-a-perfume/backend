package io.perfume.api.user.adapter.in.http;

import io.perfume.api.user.adapter.in.http.dto.SetNickNameRequestDto;
import io.perfume.api.user.application.port.in.SetUserProfileUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/v1/user")
public class SetUserProfileController {

    private final SetUserProfileUseCase setUserProfileUseCase;

    public SetUserProfileController(SetUserProfileUseCase setUserProfileUseCase) {
        this.setUserProfileUseCase = setUserProfileUseCase;
    }

    @PutMapping("/test-setPicture-endpoint")
    public ResponseEntity<Object> setProfilePicture(
            @AuthenticationPrincipal User user,
            @RequestPart MultipartFile image
    ) {
        LocalDateTime now = LocalDateTime.now();
        setUserProfileUseCase.setUserProfilePicture(user.getUsername(), image, now);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/test-put-endpoint")
    public ResponseEntity<Object> setNickName(
            @AuthenticationPrincipal User user,
            SetNickNameRequestDto dto
            ) {
        setUserProfileUseCase.setNickName(user.getUsername(), dto.name());
        return ResponseEntity.ok().build();
    }
}
