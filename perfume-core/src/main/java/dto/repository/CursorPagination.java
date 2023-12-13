package dto.repository;

import java.util.Base64;
import java.util.List;
import java.util.function.Function;

public class CursorPagination<T> {

  private final List<T> items;
  private final boolean hasNext;
  private final boolean hasPrevious;
  private final String nextCursor;
  private final String prevCursor;

  private CursorPagination(final List<T> items, final boolean hasNext, final boolean hasPrevious, final String nextCursor, final String prevCursor) {
    this.items = items;
    this.hasNext = hasNext;
    this.hasPrevious = hasPrevious;
    this.nextCursor = nextCursor;
    this.prevCursor = prevCursor;
  }

  public static <T, R> CursorPagination<R> of(final List<R> items, final CursorPagination<T> cursorPagination) {
    return new CursorPagination<>(items, cursorPagination.hasNext, cursorPagination.hasPrevious, cursorPagination.nextCursor, cursorPagination.prevCursor);
  }

  public static <T> CursorPagination<T> of(final List<T> items, final CursorPageable pageable, final Function<T, String> tokenSelector) {
    final long size = pageable.getSize();
    final var hasMoreItem = items.size() > size;
    final var resizedItems = hasMoreItem ? items.subList(0, (int) size) : items;
    final boolean hasCursor = pageable.hasCursor();

    final String nextToken = new String(Base64.getEncoder().encode(tokenSelector.apply(resizedItems.get(resizedItems.size() - 1)).getBytes()));
    final String prevToken = new String(Base64.getEncoder().encode(tokenSelector.apply(resizedItems.get(0)).getBytes()));

    if (pageable.isNext()) {
      return new CursorPagination<>(resizedItems, hasMoreItem, hasCursor, nextToken, prevToken);
    }
    return new CursorPagination<>(resizedItems, hasCursor, hasMoreItem, nextToken, prevToken);
  }

  public List<T> getItems() {
    return items;
  }

  public boolean hasNext() {
    return hasNext;
  }

  public boolean hasPrevious() {
    return hasPrevious;
  }

  public String getLastCursor() {
    return nextCursor;
  }

  public String getFirstCursor() {
    return prevCursor;
  }
}
