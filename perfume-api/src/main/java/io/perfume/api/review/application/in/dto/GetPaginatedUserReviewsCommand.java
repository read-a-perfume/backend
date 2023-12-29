package io.perfume.api.review.application.in.dto;

public class GetPaginatedUserReviewsCommand {

  private int page;

  private int size;

  public GetPaginatedUserReviewsCommand(int page, int size) {
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
