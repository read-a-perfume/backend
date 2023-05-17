package io.perfume.api.user.infrastructure.api;


import io.perfume.api.user.application.service.RegisterService;
import io.perfume.api.user.application.dto.UserResult;
import io.perfume.api.user.infrastructure.api.dto.RegisterDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1/signup")
@RequiredArgsConstructor
public class RegisterController {
    private final RegisterService registerService;

    @PostMapping("/email")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResult signUpByEmail(@RequestBody @Valid RegisterDto registerDto) {
        return registerService.signUpGeneralUserByEmail(registerDto);
    }

    @PostMapping("/check-username")
    public ResponseEntity<Void> checkUsername(@RequestBody @Valid Map<String, String> map) {
        if (registerService.validDuplicateUsername(map.get("username"))) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
