package io.perfume.api.auth.adapter.in.http;

import io.perfume.api.auth.application.port.in.MakeNewAccessTokenUseCase;
import io.perfume.api.user.domain.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
public class TokenHandlingController {
    private final MakeNewAccessTokenUseCase makeNewAccessToken;

    @GetMapping("/v1/access-token")
    public String getNewAccessToken(@RequestParam @NotBlank String accessToken) {
        return makeNewAccessToken.makeNewAccessToken(accessToken);
    }
}
