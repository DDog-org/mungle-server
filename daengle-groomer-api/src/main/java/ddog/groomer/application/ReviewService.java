package ddog.groomer.application;

import ddog.domain.groomer.Groomer;
import ddog.domain.payment.Reservation;
import ddog.domain.payment.port.ReservationPersist;
import ddog.domain.review.GroomingReview;
import ddog.domain.user.User;
import ddog.domain.user.port.UserPersist;
import ddog.groomer.application.exception.GroomingReviewException;
import ddog.groomer.application.exception.GroomingReviewExceptionType;
import ddog.groomer.application.exception.account.GroomerException;
import ddog.groomer.application.exception.account.GroomerExceptionType;
import ddog.groomer.application.exception.account.UserException;
import ddog.groomer.application.exception.account.UserExceptionType;
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
    private final ReservationPersist reservationPersist;
    private final UserPersist userPersist;
    private final GroomerPersist groomerPersist;

    private ReviewListResp mappingToGroomingReviewListResp(Page<GroomingReview> groomingReviews) {
        List<ReviewSummaryResp> groomingReviewList = groomingReviews.stream().map(groomingReview -> {

            User reviewer = userPersist.findByAccountId(groomingReview.getReviewerId())
                    .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));

            Reservation savedReservation = reservationPersist.findByReservationId(groomingReview.getReservationId())
                    .orElseThrow(() -> new GroomingReviewException(GroomingReviewExceptionType.GROOMING_REVIEW_RESERVATION_NOT_FOUND));

            return ReviewSummaryResp.builder()
                    .groomingReviewId(groomingReview.getGroomingReviewId())
                    .reviewerName(reviewer.getNickname())
                    .reviewerImageUrl(reviewer.getUserImage())
                    .groomerId(groomingReview.getGroomerId())
                    .groomingKeywordList(groomingReview.getGroomingKeywordList())
                    .revieweeName(groomingReview.getRevieweeName())
                    .createdAt(savedReservation.getSchedule())
                    .starRating(groomingReview.getStarRating())
                    .content(groomingReview.getContent())
                    .imageUrlList(groomingReview.getImageUrlList())
                    .build();
        }).toList();    //groomingReviewList

        return ReviewListResp.builder()
                .reviewCount(groomingReviews.getTotalElements())
                .reviewList(groomingReviewList)
                .build();
    }

    public ReviewListResp findReviewList(Long accountId, int page, int size) {
        Groomer savedGroomer = groomerPersist.findByAccountId(accountId)
                .orElseThrow(() -> new GroomerException(GroomerExceptionType.GROOMER_NOT_FOUND));

        Pageable pageable = PageRequest.of(page, size);
        Page<GroomingReview> groomingReviews = groomingReviewPersist.findByGroomerId(savedGroomer.getGroomerId(), pageable);

        return mappingToGroomingReviewListResp(groomingReviews);
    }
}
