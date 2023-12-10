package dto.repository;

public class CursorPageable<T> {

  private final long size;

  private final T cursor;

  private final CursorDirection direction;

  public CursorPageable(final long size, final CursorDirection direction, final T cursor) {
    this.size = size;
    this.cursor = cursor;
    this.direction = direction;
  }

  public long getSize() {
    return size;
  }

  public T getCursor() {
    return cursor;
  }

  public CursorDirection getDirection() {
    return direction;
  }
}
