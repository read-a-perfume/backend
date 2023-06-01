package io.perfume.api.auth.adapter.in.http;

import io.perfume.api.auth.application.port.in.MakeNewAccessTokenUseCase;
import io.perfume.api.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenHandlingController {
    private final MakeNewAccessTokenUseCase makeNewAccessToken;
    @GetMapping
    public String getAccessKey(@AuthenticationPrincipal User user) {
        return makeNewAccessToken.makeNewAccessToken(user.getEmail());
    }
}
