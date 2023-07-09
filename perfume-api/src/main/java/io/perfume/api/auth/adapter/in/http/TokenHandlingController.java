package io.perfume.api.auth.adapter.in.http;

import io.perfume.api.auth.application.port.in.MakeNewTokenUseCase;
import io.perfume.api.auth.application.port.in.dto.ReissuedTokenResult;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
public class TokenHandlingController {
  private final MakeNewTokenUseCase makeNewTokenUseCase;

  @GetMapping("/v1/reissue")
  public ResponseEntity<Void> reissueAccessToken(
      @CookieValue(name = "X-Refresh-Token") String refreshToken,
      @RequestHeader(name = "Authorization") String accessToken) {
    ReissuedTokenResult reissuedTokenResult =
        makeNewTokenUseCase.reissueAccessToken(accessToken, refreshToken, LocalDateTime.now());
    String cookie = ResponseCookie.from("X-Refresh-Token", reissuedTokenResult.refreshToken())
        .httpOnly(true)
        .secure(true).build().toString();
    return ResponseEntity.ok()
        .header("Authorization", reissuedTokenResult.accessToken())
        .header(HttpHeaders.SET_COOKIE, cookie).build();
  }
}
