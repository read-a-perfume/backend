package io.perfume.api.user.adapter.in.http;


import io.perfume.api.user.adapter.in.http.dto.CheckUsernameRequestDto;
import io.perfume.api.user.adapter.in.http.dto.EmailSignUpResponseDto;
import io.perfume.api.user.adapter.in.http.dto.EmailVerifyConfirmRequestDto;
import io.perfume.api.user.adapter.in.http.dto.EmailVerifyConfirmResponseDto;
import io.perfume.api.user.adapter.in.http.dto.RegisterDto;
import io.perfume.api.user.adapter.in.http.dto.SendEmailVerifyCodeRequestDto;
import io.perfume.api.user.adapter.in.http.dto.SendEmailVerifyCodeResponseDto;
import io.perfume.api.user.application.port.in.dto.ConfirmEmailVerifyResult;
import io.perfume.api.user.application.port.in.dto.SendVerificationCodeCommand;
import io.perfume.api.user.application.port.in.dto.SendVerificationCodeResult;
import io.perfume.api.user.application.port.in.dto.SignUpGeneralUserCommand;
import io.perfume.api.user.application.port.in.dto.UserResult;
import io.perfume.api.user.application.service.RegisterService;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/signup")
@RequiredArgsConstructor
public class RegisterController {
  private final RegisterService registerService;

  @PostMapping("/email")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<EmailSignUpResponseDto> signUpByEmail(
      @RequestBody @Valid RegisterDto registerDto) {
    SignUpGeneralUserCommand command = new SignUpGeneralUserCommand(
        registerDto.username(),
        registerDto.password(),
        registerDto.email(),
        registerDto.marketingConsent(),
        registerDto.promotionConsent(),
        registerDto.name()
    );
    UserResult result = registerService.signUpGeneralUserByEmail(command);

    return ResponseEntity.status(HttpStatus.CREATED).body(new EmailSignUpResponseDto(
        result.username(),
        result.email(),
        result.name()
    ));
  }

  @PostMapping("/check-username")
  public ResponseEntity<Void> checkUsername(@RequestBody @Valid CheckUsernameRequestDto dto) {
    if (registerService.validDuplicateUsername(dto.username())) {
      return ResponseEntity.status(HttpStatus.OK).build();
    }

    return ResponseEntity.status(HttpStatus.CONFLICT).build();
  }

  @PostMapping("/email-verify/confirm")
  public ResponseEntity<EmailVerifyConfirmResponseDto> confirmEmail(
      @RequestBody @Valid EmailVerifyConfirmRequestDto dto) {
    LocalDateTime now = LocalDateTime.now();
    ConfirmEmailVerifyResult result =
        registerService.confirmEmailVerify(dto.key(), dto.code(), now);

    return ResponseEntity.ok(
        new EmailVerifyConfirmResponseDto(result.email(), result.verifiedAt()));
  }

  @PostMapping("/email-verify/request")
  public ResponseEntity<SendEmailVerifyCodeResponseDto> requestEmailVerify(
      @RequestBody @Valid SendEmailVerifyCodeRequestDto dto) {
    LocalDateTime now = LocalDateTime.now();
    SendVerificationCodeCommand command = new SendVerificationCodeCommand(dto.email(), now);
    SendVerificationCodeResult result = registerService.sendEmailVerifyCode(command);

    return ResponseEntity
        .ok(new SendEmailVerifyCodeResponseDto(result.key(), result.sentAt()));
  }
}
