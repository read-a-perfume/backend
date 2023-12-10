package dto.repository;

public enum CursorDirection {
  NEXT(true),
  PREVIOUS(false);

  private final boolean direction;

  CursorDirection(final boolean direction) {
    this.direction = direction;
  }

  public boolean isNext() {
    return direction;
  }

  public boolean isPrev() {
    return !direction;
  }
}
