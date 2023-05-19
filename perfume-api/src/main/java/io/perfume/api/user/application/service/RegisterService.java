package io.perfume.api.user.application.service;

import io.perfume.api.auth.application.port.in.CheckEmailCertificateUseCase;
import io.perfume.api.auth.application.port.in.CreateVerificationCodeUseCase;
import io.perfume.api.auth.application.port.in.dto.CheckEmailCertificateCommand;
import io.perfume.api.auth.application.port.in.dto.CheckEmailCertificateResult;
import io.perfume.api.auth.application.port.in.dto.CreateVerificationCodeCommand;
import io.perfume.api.auth.application.port.in.dto.CreateVerificationCodeResult;
import io.perfume.api.user.application.dto.UserResult;
import io.perfume.api.user.application.exception.FailedRegisterException;
import io.perfume.api.user.application.port.in.dto.ConfirmEmailVerifyResult;
import io.perfume.api.user.application.port.in.dto.SendVerificationCodeCommand;
import io.perfume.api.user.application.port.in.dto.SendVerificationCodeResult;
import io.perfume.api.user.application.port.out.UserRepository;
import io.perfume.api.user.domain.User;
import io.perfume.api.user.infrastructure.api.dto.RegisterDto;
import lombok.RequiredArgsConstructor;
import mailer.MailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RegisterService {

    private final UserRepository userRepository;

    private final CheckEmailCertificateUseCase checkEmailCertificateUseCase;

    private final CreateVerificationCodeUseCase createVerificationCodeUseCase;

    private final MailSender mailSender;

    @Transactional
    public UserResult signUpGeneralUserByEmail(RegisterDto registerDto) {
        User user = User.generalUserJoin(
                registerDto.username(),
                registerDto.email(),
                registerDto.password(), // TODO : Password encoding 추가하기
                registerDto.name(),
                registerDto.marketingConsent(),
                registerDto.promotionConsent());

        return toDto(userRepository.save(user).orElseThrow(FailedRegisterException::new));
    }

    public boolean validDuplicateUsername(String username) {
        try {
            return userRepository.findByUsername(username).isEmpty();
        } catch (Exception e) {
            return true;
        }
    }

    public ConfirmEmailVerifyResult confirmEmailVerify(String code, String key, LocalDateTime now) {
        CheckEmailCertificateCommand command = new CheckEmailCertificateCommand(code, key);
        CheckEmailCertificateResult result = checkEmailCertificateUseCase.checkEmailCertificate(command, now);

        return new ConfirmEmailVerifyResult("", now, "");
    }

    public SendVerificationCodeResult sendEmailVerifyCode(SendVerificationCodeCommand command) {
        CreateVerificationCodeCommand createVerificationCodeCommand = new CreateVerificationCodeCommand(command.email(), command.now());
        CreateVerificationCodeResult result = createVerificationCodeUseCase.createVerificationCode(createVerificationCodeCommand);
        LocalDateTime sentAt = mailSender.send(command.email(), "이메일 인증을 완료해주세요.", result.code());

        return new SendVerificationCodeResult(result.signKey(), sentAt);
    }

    private UserResult toDto(User user) {
        return new UserResult(user.getUsername(), user.getEmail(), user.getName(), user.getCreatedAt());
    }
}
