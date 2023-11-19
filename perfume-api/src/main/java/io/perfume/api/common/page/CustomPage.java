package io.perfume.api.common.page;

import java.util.List;
import lombok.Getter;
import org.springframework.data.domain.PageImpl;

@Getter
public class CustomPage<T> {
  private final List<T> content;
  private final boolean first;
  private final boolean last;
  private final boolean hasNext;
  private final int totalPages;
  private final long totalElements;
  private final int pageNumber;
  private final int size;

  public CustomPage(PageImpl<T> pageImpl) {
    this.content = pageImpl.getContent();
    this.first = pageImpl.isFirst();
    this.last = pageImpl.isLast();
    this.hasNext = pageImpl.hasNext();
    this.totalPages = pageImpl.getTotalPages();
    this.totalElements = pageImpl.getTotalElements();
    this.pageNumber = pageImpl.getNumber() + 1;
    this.size = pageImpl.getSize();
  }

  public CustomPage(List<T> content, CustomPage<?> customPage) {
    this.content = content;
    this.first = customPage.isFirst();
    this.last = customPage.isLast();
    this.hasNext = customPage.isHasNext();
    this.totalPages = customPage.getTotalPages();
    this.totalElements = customPage.getTotalElements();
    this.pageNumber = customPage.getPageNumber();
    this.size = customPage.getSize();
  }
}
