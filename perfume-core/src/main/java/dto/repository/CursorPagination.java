package dto.repository;

import java.util.List;

public class CursorPagination<T> {

  private final List<T> items;

  private final boolean hasNext;

  private final boolean hasPrevious;

  private CursorPagination(final List<T> items, final boolean hasNext,
                           final boolean hasPrevious) {
    this.items = items;
    this.hasNext = hasNext;
    this.hasPrevious = hasPrevious;
  }

  public static <T> CursorPagination<T> of(final List<T> items, final Long size,
                                           final CursorDirection direction,
                                           final boolean isSelectCursor) {
    final var hasMoreItem = items.size() > size;
    final var resizedItems = hasMoreItem ? items.subList(0, size.intValue()) : items;

    if (direction == CursorDirection.NEXT) {
      return new CursorPagination<>(resizedItems, hasMoreItem, isSelectCursor);
    }
    return new CursorPagination<>(resizedItems, isSelectCursor, hasMoreItem);
  }

  public static <T> CursorPagination<T> of(final List<T> items, final boolean hasNext, final boolean hasPrevious) {
    return new CursorPagination<>(items, hasNext, hasPrevious);
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

  public T getLastCursor() {
    if (items.isEmpty()) {
      return null;
    }

    return items.get(items.size() - 1);
  }

  public T getFirstCursor() {
    if (items.isEmpty()) {
      return null;
    }

    return items.get(0);
  }
}
