package io.perfume.api.review.application.in.dto;

public class GetPaginatedReviewsCommand {

  private int page;

  private int size;

  public GetPaginatedReviewsCommand(int page, int size) {
    this.page = page;
    this.size = size;
  }

  public int offset() {
    return (page - 1) * size;
  }

  public int limit() {
    return size;
  }
}
