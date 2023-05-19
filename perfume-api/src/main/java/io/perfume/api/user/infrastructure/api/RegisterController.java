package io.perfume.api.user.infrastructure.api;


import io.perfume.api.user.application.dto.UserResult;
import io.perfume.api.user.application.port.in.dto.SendEmailVerifyCodeRequestDto;
import io.perfume.api.user.application.service.RegisterService;
import io.perfume.api.user.infrastructure.api.dto.EmailVerifyConfirmRequestDto;
import io.perfume.api.user.infrastructure.api.dto.EmailVerifyConfirmResponseDto;
import io.perfume.api.user.infrastructure.api.dto.RegisterDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @PostMapping("/email-verify/confirm")
    public ResponseEntity<EmailVerifyConfirmResponseDto> confirmEmail(@RequestBody @Valid EmailVerifyConfirmRequestDto dto) {
        return ResponseEntity.ok(new EmailVerifyConfirmResponseDto("example@mail.com", LocalDateTime.now()));
    }

    @PostMapping("/email-verify/request")
    public ResponseEntity<Void> requestEmailVerify(@RequestBody @Valid SendEmailVerifyCodeRequestDto dto) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
