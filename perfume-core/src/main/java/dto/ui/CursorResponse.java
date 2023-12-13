package dto.ui;

import java.util.List;

public class CursorResponse<T> {

  private final List<T> items;

  private final boolean hasNext;

  private final boolean hasPrev;

  private final String nextCursor;

  private final String prevCursor;

  private CursorResponse(
      List<T> items, boolean hasNext, boolean hasPrev, String nextCursor, String prevCursor) {
    this.items = items;
    this.hasNext = hasNext;
    this.hasPrev = hasPrev;
    this.nextCursor = nextCursor;
    this.prevCursor = prevCursor;
  }

  public static <T> CursorResponse<T> of(
      List<T> items, boolean hasNext, boolean hasPrev, String nextCursor, String prevCursor) {
    return new CursorResponse<>(items, hasNext, hasPrev, nextCursor, prevCursor);
  }

  public List<T> getItems() {
    return items;
  }

  public boolean isHasNext() {
    return hasNext;
  }

  public boolean isHasPrev() {
    return hasPrev;
  }

  public String getNextCursor() {
    return nextCursor;
  }

  public String getPrevCursor() {
    return prevCursor;
  }
}
