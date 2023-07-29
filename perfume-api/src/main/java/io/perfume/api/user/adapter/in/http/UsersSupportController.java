package io.perfume.api.user.adapter.in.http;

import io.perfume.api.common.auth.UserPrincipal;
import io.perfume.api.user.application.port.in.FindEncryptedUsernameUseCase;
import io.perfume.api.user.application.port.in.LeaveUserUseCase;
import io.perfume.api.user.application.port.in.SendResetPasswordMailUseCase;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class UsersSupportController {

    private final FindEncryptedUsernameUseCase findEncryptedUsernameUseCase;
    private final SendResetPasswordMailUseCase resetPasswordUserCase;

    private final LeaveUserUseCase leaveUserUseCase;
    /**
     * TODO OAUTH 가입자의 경우 uesrname도 같이 넣는 흐름이 이상하다.
     */
    @GetMapping("/email/reset-passowrd")
    public ResponseEntity<Object> sendPasswordToUsersEmail(
            @RequestParam @Email String email,
            @RequestParam @NotBlank String username) {

        resetPasswordUserCase.sendNewPasswordToEmail(email, username);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/find-username")
    public ResponseEntity<Object> sendUsernameToUsersEmail(@RequestParam @Email String email) {

        String encryptedUsername = findEncryptedUsernameUseCase.findEncryptedUsername(email);
        return ResponseEntity.ok(encryptedUsername);
    }

    @DeleteMapping("/user")
    public ResponseEntity leaveUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        leaveUserUseCase.leave(userPrincipal.getUser().getId());
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
