package dto.ui;

import java.util.List;

public class CursorResponse<T, CURSOR> {

  private final List<T> items;

  private final boolean hasNext;

  private final boolean hasPrev;

  private final CURSOR nextCursor;

  private final CURSOR prevCursor;

  private CursorResponse(
      List<T> items, boolean hasNext, boolean hasPrev, CURSOR nextCursor, CURSOR prevCursor) {
    this.items = items;
    this.hasNext = hasNext;
    this.hasPrev = hasPrev;
    this.nextCursor = nextCursor;
    this.prevCursor = prevCursor;
  }

  public static <T, CURSOR> CursorResponse<T, CURSOR> of(
      List<T> items, boolean hasNext, boolean hasPrev, CURSOR nextCursor, CURSOR prevCursor) {
    return new CursorResponse<>(items, hasNext, hasPrev, nextCursor, prevCursor);
  }
}
