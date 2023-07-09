package io.perfume.api.user.stub;

import io.perfume.api.auth.application.port.in.CheckEmailCertificateUseCase;
import io.perfume.api.auth.application.port.in.dto.CheckEmailCertificateCommand;
import io.perfume.api.auth.application.port.in.dto.CheckEmailCertificateResult;
import java.util.ArrayList;
import java.util.List;

public class StubCheckEmailCertificateUseCase implements CheckEmailCertificateUseCase {

  private final List<CheckEmailCertificateResult> store = new ArrayList<>();

  @Override
  public CheckEmailCertificateResult checkEmailCertificate(
      CheckEmailCertificateCommand checkEmailCertificateCommand) {
    return store.remove(0);
  }

  public void add(CheckEmailCertificateResult checkEmailCertificateResult) {
    store.add(checkEmailCertificateResult);
  }

  public void clear() {
    store.clear();
  }
}
