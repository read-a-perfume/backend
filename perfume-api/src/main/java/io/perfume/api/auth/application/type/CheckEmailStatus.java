package io.perfume.api.auth.application.type;

public enum CheckEmailStatus {
  MATCH(),
  NOT_MATCH(),
  EXPIRED(),
  NOT_FOUND();

  CheckEmailStatus() {}
}
