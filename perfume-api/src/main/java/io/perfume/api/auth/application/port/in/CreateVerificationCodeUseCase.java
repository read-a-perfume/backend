package io.perfume.api.auth.application.port.in;

import io.perfume.api.auth.application.port.in.dto.CreateVerificationCodeCommand;
import io.perfume.api.auth.application.port.in.dto.CreateVerificationCodeResult;

public interface CreateVerificationCodeUseCase {

    CreateVerificationCodeResult createVerificationCode(CreateVerificationCodeCommand createVerificationCodeCommand);
}
