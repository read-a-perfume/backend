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

  private CursorPagination(
      final List<T> items,
      final boolean hasNext,
      final boolean hasPrevious,
      final String nextCursor,
      final String prevCursor) {
    this.items = items;
    this.hasNext = hasNext;
    this.hasPrevious = hasPrevious;
    this.nextCursor = nextCursor;
    this.prevCursor = prevCursor;
  }

  public static <T, R> CursorPagination<R> of(
      final List<R> items, final CursorPagination<T> cursorPagination) {
    return new CursorPagination<>(
        items,
        cursorPagination.hasNext,
        cursorPagination.hasPrevious,
        cursorPagination.nextCursor,
        cursorPagination.prevCursor);
  }

  public static <T> CursorPagination<T> of(
      final List<T> items, final CursorPageable pageable, final Function<T, String> tokenSelector) {

    final List<T> paginatedItems = paginateItems(items, pageable);
    final String nextToken = encodeNextToken(paginatedItems, tokenSelector);
    final String prevToken = encodePreviousToken(paginatedItems, tokenSelector);

    final boolean hasRequestedWithCursor = pageable.hasCursor();
    final boolean hasMoreItemsThanPage = isItemsSizeGreaterThanPageSize(items, pageable);

    return new CursorPagination<>(
        paginatedItems,
        hasMoreItemsThanPage,
        hasRequestedWithCursor,
        hasMoreItemsThanPage ? nextToken : null,
        hasRequestedWithCursor ? prevToken : null);
  }

  private static <T> List<T> paginateItems(final List<T> items, final CursorPageable pageable) {
    return isItemsSizeGreaterThanPageSize(items, pageable)
        ? items.subList(0, pageable.getSize())
        : items;
  }

  private static <T> boolean isItemsSizeGreaterThanPageSize(
      final List<T> items, final CursorPageable pageable) {
    final long pageSize = pageable.getSize();
    return items.size() > pageSize;
  }

  private static <T> String encodeNextToken(
      final List<T> items, final Function<T, String> tokenSelector) {
    if (items.isEmpty()) {
      return null;
    }

    final T lastItem = items.get(items.size() - 1);
    return new String(Base64.getEncoder().encode(tokenSelector.apply(lastItem).getBytes()));
  }

  private static <T> String encodePreviousToken(
      final List<T> items, final Function<T, String> tokenSelector) {
    if (items.isEmpty()) {
      return null;
    }

    final T firstItem = items.get(0);
    return new String(Base64.getEncoder().encode(tokenSelector.apply(firstItem).getBytes()));
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

  public String getNextCursor() {
    return nextCursor;
  }

  public String getPreviousCursor() {
    return prevCursor;
  }
}
