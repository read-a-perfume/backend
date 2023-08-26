package io.perfume.api.review.stub;

import io.perfume.api.review.application.out.TagQueryRepository;
import io.perfume.api.review.application.out.TagRepository;
import io.perfume.api.review.domain.ReviewTag;
import io.perfume.api.review.domain.Tag;
import java.util.ArrayList;
import java.util.List;

public class StubTagRepository implements TagRepository, TagQueryRepository {

  private final List<Tag> tags = new ArrayList<>();

  private final List<ReviewTag> reviewTags = new ArrayList<>();

  @Override
  public List<Tag> findByIds(List<Long> ids) {
    return tags;
  }

  @Override
  public List<ReviewTag> findReviewTags(Long reviewId) {
    return reviewTags;
  }

  @Override
  public ReviewTag save(ReviewTag tags) {
    reviewTags.add(tags);
    return tags;
  }

  @Override
  public List<ReviewTag> saveAll(List<ReviewTag> tags) {
    reviewTags.addAll(tags);
    return tags;
  }

  public void clear() {
    tags.clear();
  }

  public void addTag(Tag tag) {
    tags.add(tag);
  }

  public void addReviewTag(ReviewTag reviewTag) {
    reviewTags.add(reviewTag);
  }
}
