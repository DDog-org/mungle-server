package ddog.vet.application;

import ddog.domain.review.CareReview;
import ddog.domain.vet.Vet;
import ddog.domain.review.port.CareReviewPersist;
import ddog.domain.vet.port.VetPersist;
import ddog.vet.application.exception.account.VetException;
import ddog.vet.application.exception.account.VetExceptionType;
import ddog.vet.presentation.review.dto.ReviewListResp;
import ddog.vet.presentation.review.dto.ReviewSummaryResp;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final CareReviewPersist careReviewPersist;
    private final VetPersist vetPersist;

    public ReviewListResp findReviewList(Long accountId, int page, int size) {
        Vet savedVet = vetPersist.findByAccountId(accountId)
                .orElseThrow(() -> new VetException(VetExceptionType.VET_NOT_FOUND));

        Pageable pageable = PageRequest.of(page, size);
        Page<CareReview> careReviews = careReviewPersist.findByVetId(savedVet.getVetId(), pageable);

        return mappingToCareReviewListResp(careReviews);
    }

    private static ReviewListResp mappingToCareReviewListResp(Page<CareReview> careReviews) {
        List<ReviewSummaryResp> careReviewList = careReviews.stream()
                .map(careReview -> ReviewSummaryResp.builder()
                        .careReviewId(careReview.getCareReviewId())
                        .vetId(careReview.getVetId())
                        .careKeywordReviewList(careReview.getCareKeywordReviewList())
                        .revieweeName(careReview.getRevieweeName())
                        .starRating(careReview.getStarRating())
                        .content(careReview.getContent())
                        .imageUrlList(careReview.getImageUrlList())
                        .build())
                .toList();

        return ReviewListResp.builder()
                .reviewCount(careReviews.getTotalElements())
                .reviewList(careReviewList)
                .build();
    }
}
