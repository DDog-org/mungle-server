package ddog.groomer.application;

import ddog.domain.groomer.Groomer;
import ddog.domain.review.GroomingReview;
import ddog.groomer.application.exception.GroomerException;
import ddog.groomer.application.exception.GroomerExceptionType;
import ddog.groomer.presentation.review.dto.ReviewSummaryResp;
import ddog.persistence.mysql.port.GroomerPersist;
import ddog.persistence.mysql.port.GroomingReviewPersist;
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

    public List<ReviewSummaryResp> findReviewList(Long accountId, int page, int size) {
        Groomer savedGroomer = groomerPersist.findBy(accountId)
                .orElseThrow(() -> new GroomerException(GroomerExceptionType.GROOMER_NOT_FOUND));

        Pageable pageable = PageRequest.of(page, size);
        Page<GroomingReview> groomingReviews = groomingReviewPersist.findByGroomerId(savedGroomer.getGroomerId(), pageable);

        return groomingReviews.stream()
                .map(groomingReview -> ReviewSummaryResp.builder()
                        .groomingReviewId(groomingReview.getGroomingReviewId())
                        .groomerId(groomingReview.getGroomerId())
                        .groomingKeywordReviewList(groomingReview.getGroomingKeywordReviewList())
                        .revieweeName(groomingReview.getRevieweeName())
                        .starRating(groomingReview.getStarRating())
                        .content(groomingReview.getContent())
                        .build())
                .toList();
    }
}
