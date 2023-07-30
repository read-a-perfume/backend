package io.perfume.api.auth.adapter.in.http;

import io.perfume.api.auth.application.port.in.MakeNewTokenUseCase;
import io.perfume.api.auth.application.port.in.dto.ReissuedTokenResult;
import io.perfume.api.common.auth.Constants;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
public class TokenHandlingController {

  private final MakeNewTokenUseCase makeNewTokenUseCase;

  @GetMapping("/v1/reissue")
  public ResponseEntity<Void> reissueAccessToken(
      @CookieValue(name = Constants.ACCESS_TOKEN_KEY) String accessToken,
      @CookieValue(name = Constants.REFRESH_TOKEN_KEY) String refreshToken) {
    var reissuedTokenResult =
        makeNewTokenUseCase.reissueAccessToken(accessToken, refreshToken, LocalDateTime.now());

    return ResponseEntity
        .ok()
        .headers(createAuthenticationCookie(reissuedTokenResult))
        .build();
  }

  private HttpHeaders createAuthenticationCookie(ReissuedTokenResult reissuedTokenResult) {
    var responseHeaders = new HttpHeaders();

    responseHeaders.add(Constants.ACCESS_TOKEN_KEY, reissuedTokenResult.accessToken());
    responseHeaders.add(Constants.REFRESH_TOKEN_KEY, reissuedTokenResult.refreshToken());

    return responseHeaders;
  }
}
