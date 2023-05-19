package io.perfume.api.user.stub;

import io.perfume.api.auth.application.port.in.CreateVerificationCodeUseCase;
import io.perfume.api.auth.application.port.in.dto.CreateVerificationCodeCommand;
import io.perfume.api.auth.application.port.in.dto.CreateVerificationCodeResult;

public class StubCreateVerificationCodeUseCase implements CreateVerificationCodeUseCase {

    private CreateVerificationCodeResult createVerificationCodeResult;

    @Override
    public CreateVerificationCodeResult createVerificationCode(CreateVerificationCodeCommand createVerificationCodeCommand) {
        return createVerificationCodeResult;
    }

    public void setCreateVerificationCodeResult(CreateVerificationCodeResult createVerificationCodeResult) {
        this.createVerificationCodeResult = createVerificationCodeResult;
    }

    public void clear() {
        this.createVerificationCodeResult = null;
    }
}
