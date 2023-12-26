package dto.repository;

import java.util.Base64;
import java.util.Optional;
import java.util.function.Function;

public class CursorPageable {

  private final int size;

  private final String cursor;

  private final CursorDirection direction;

  private static final int PADDING_SIZE = 1;

  public CursorPageable(final int size, final CursorDirection direction, final String cursor) {
    this.size = size;
    this.cursor = cursor;
    this.direction = direction;
  }

  public int getSize() {
    return size;
  }

  public long getFetchSize() {
    return size + PADDING_SIZE;
  }

  public <T> Optional<T> getCursor(Function<String, T> converter) {
    try {
      final String decoded = new String(Base64.getDecoder().decode(cursor));
      return Optional.ofNullable(converter.apply(decoded));
    } catch (Exception e) {
      return Optional.empty();
    }
  }

  public CursorDirection getDirection() {
    return direction;
  }

  public boolean isNext() {
    return direction.isNext();
  }

  public boolean isPrev() {
    return !isNext();
  }

  public boolean hasCursor() {
    return cursor != null;
  }
}
