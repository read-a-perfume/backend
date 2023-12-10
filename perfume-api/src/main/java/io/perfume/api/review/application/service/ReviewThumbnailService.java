package io.perfume.api.review.application.service;

import io.perfume.api.review.application.in.thumbnail.AddReviewThumbnailUseCase;
import io.perfume.api.review.application.out.thumbnail.ReviewThumbnailRepository;
import io.perfume.api.review.domain.ReviewThumbnail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewThumbnailService implements AddReviewThumbnailUseCase {

    private final ReviewThumbnailRepository reviewThumbnailRepository;

    @Override
    public List<ReviewThumbnail> addThumbnails(Long reviewId, List<Long> thumbnailIds, LocalDateTime now) {
        if (thumbnailIds.isEmpty()) {
            return List.of();
        }

        return reviewThumbnailRepository.saveAll(
                thumbnailIds.stream()
                        .map(thumbnailId -> new ReviewThumbnail(reviewId, thumbnailId, now, now, null))
                        .toList()
        );
    }
}
