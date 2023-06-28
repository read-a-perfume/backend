package io.perfume.api.auth.adapter.in.http;

import io.perfume.api.auth.application.port.in.MakeNewTokenUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jwt.JsonWebTokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
public class TokenHandlingController {
    private final MakeNewTokenUseCase makeNewTokenUseCase;
    private final JsonWebTokenGenerator jsonWebTokenGenerator;

    @GetMapping("/v1/access-token")
    public String reissueAccessToken(@CookieValue(name = "X-Refresh-Token") String refreshToken, HttpServletRequest request) {
        return makeNewTokenUseCase.reissueAccessToken(jsonWebTokenGenerator.getTokenFromHeader(request), refreshToken);
    }
}
