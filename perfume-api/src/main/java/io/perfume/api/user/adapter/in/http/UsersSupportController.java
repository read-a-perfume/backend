package io.perfume.api.user.adapter.in.http;

import io.perfume.api.user.application.port.in.FindUserUseCase;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class UsersSupportController {

    private final FindUserUseCase findUserUseCase;

    // TODO OAUTH 가입자의 경우 uesrname도 같이 넣는 흐름이 이상하다.
    @GetMapping("/send-password-to-mail")
    public ResponseEntity<Object> sendPasswordToUsersEmail(
            @RequestParam @Email String email,
            @RequestParam @NotBlank String username) {

        findUserUseCase.sendPasswordToEmail(email, username);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/find-username")
    public ResponseEntity<Object> sendUsernameToUsersEmail(@RequestParam @Email String email) {

        String encryptedUsername = findUserUseCase.getEncryptedUsernameByEmail(email);
        return ResponseEntity.ok(encryptedUsername);
    }

}
