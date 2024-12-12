package ddog.vet.application;

import ddog.domain.payment.port.ReservationPersist;
import ddog.domain.review.CareReview;
import ddog.domain.review.port.CareReviewPersist;
import ddog.domain.user.User;
import ddog.domain.user.port.UserPersist;
import ddog.domain.vet.Vet;
import ddog.domain.vet.port.VetPersist;
import ddog.vet.application.exception.CareReviewException;
import ddog.vet.application.exception.CareReviewExceptionType;
import ddog.vet.application.exception.account.UserException;
import ddog.vet.application.exception.account.UserExceptionType;
import ddog.vet.application.exception.account.VetException;
import ddog.vet.application.exception.account.VetExceptionType;
import ddog.vet.presentation.review.dto.ReviewListResp;
import ddog.vet.presentation.review.dto.ReviewSummaryResp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final CareReviewPersist careReviewPersist;
    private final ReservationPersist reservationPersist;
    private final UserPersist userPersist;
    private final VetPersist vetPersist;

    public ReviewListResp findReviewList(Long accountId, int page, int size) {
        Vet savedVet = vetPersist.findByAccountId(accountId)
                .orElseThrow(() -> new VetException(VetExceptionType.VET_NOT_FOUND));

        Pageable pageable = PageRequest.of(page, size);
        Page<CareReview> careReviews = careReviewPersist.findByRevieweeId(savedVet.getAccountId(), pageable);

        return mappingToCareReviewListResp(careReviews);
    }

    private ReviewListResp mappingToCareReviewListResp(Page<CareReview> careReviews) {
        List<ReviewSummaryResp> careReviewList = careReviews.stream().map(careReview -> {

            User reviewer = userPersist.findByAccountId(careReview.getReviewerId())
                    .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));

            reservationPersist.findByReservationId(careReview.getReservationId())
                    .orElseThrow(() -> new CareReviewException(CareReviewExceptionType.CARE_REVIEW_RESERVATION_NOT_FOUND));

            return ReviewSummaryResp.builder()
                    .careReviewId(careReview.getCareReviewId())
                    .reviewerName(reviewer.getNickname())
                    .reviewerImageUrl(reviewer.getImageUrl())
                    .vetId(careReview.getVetId())
                    .careKeywordList(careReview.getCareKeywordList())
                    .revieweeName(careReview.getRevieweeName())
                    .createdAt(careReview.getCreatedAt())
                    .starRating(careReview.getStarRating())
                    .content(careReview.getContent())
                    .imageUrlList(careReview.getImageUrlList())
                    .build();
        }).toList();    //careReviewList

        return ReviewListResp.builder()
                .reviewCount(careReviews.getTotalElements())
                .reviewList(careReviewList)
                .build();
    }
}
