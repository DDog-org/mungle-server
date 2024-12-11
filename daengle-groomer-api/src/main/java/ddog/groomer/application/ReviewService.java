package ddog.groomer.application;

import ddog.domain.groomer.Groomer;
import ddog.domain.review.GroomingReview;
import ddog.groomer.application.exception.account.GroomerException;
import ddog.groomer.application.exception.account.GroomerExceptionType;
import ddog.groomer.presentation.review.dto.ReviewListResp;
import ddog.groomer.presentation.review.dto.ReviewSummaryResp;
import ddog.domain.groomer.port.GroomerPersist;
import ddog.domain.review.port.GroomingReviewPersist;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ReviewService {

    private final GroomingReviewPersist groomingReviewPersist;
    private final GroomerPersist groomerPersist;

    public ReviewListResp findReviewList(Long accountId, int page, int size) {
        Groomer savedGroomer = groomerPersist.findByAccountId(accountId)
                .orElseThrow(() -> new GroomerException(GroomerExceptionType.GROOMER_NOT_FOUND));

        Pageable pageable = PageRequest.of(page, size);
        Page<GroomingReview> groomingReviews = groomingReviewPersist.findByGroomerId(savedGroomer.getGroomerId(), pageable);

        return mappingToGroomingReviewListResp(groomingReviews);
    }

    private static ReviewListResp mappingToGroomingReviewListResp(Page<GroomingReview> groomingReviews) {
        List<ReviewSummaryResp> groomingReviewList = groomingReviews.stream()
                .map(groomingReview -> ReviewSummaryResp.builder()
                        .groomingReviewId(groomingReview.getGroomingReviewId())
                        .groomerId(groomingReview.getGroomerId())
                        .groomingKeywordReviewList(groomingReview.getGroomingKeywordReviewList())
                        .revieweeName(groomingReview.getRevieweeName())
                        .starRating(groomingReview.getStarRating())
                        .content(groomingReview.getContent())
                        .imageUrlList(groomingReview.getImageUrlList())
                        .build())
                .toList();

        return ReviewListResp.builder()
                .reviewCount(groomingReviews.getTotalElements())
                .reviewList(groomingReviewList)
                .build();
    }
}
