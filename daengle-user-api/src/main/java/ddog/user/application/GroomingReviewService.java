package ddog.user.application;

import ddog.domain.payment.Reservation;
import ddog.domain.review.GroomingReview;
import ddog.domain.review.dto.ModifyGroomingReviewInfo;
import ddog.domain.review.dto.PostGroomingReviewInfo;
import ddog.domain.user.User;
import ddog.persistence.mysql.port.GroomingReviewPersist;
import ddog.persistence.mysql.port.ReservationPersist;
import ddog.persistence.mysql.port.UserPersist;
import ddog.user.application.exception.*;
import ddog.user.presentation.review.dto.GroomingReviewSummaryResp;
import ddog.user.presentation.review.dto.ReviewResp;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroomingReviewService {

    private final GroomingReviewPersist groomingReviewPersist;
    private final ReservationPersist reservationPersist;
    private final UserPersist userPersist;

    public ReviewResp postReview(PostGroomingReviewInfo postGroomingReviewInfo) {
        Reservation reservation = reservationPersist.findBy(postGroomingReviewInfo.getReservationId()).orElseThrow(()
                -> new ReservationException(ReservationExceptionType.RESERVATION_NOT_FOUND));

        GroomingReview groomingReviewToSave = GroomingReview.createBy(reservation, postGroomingReviewInfo);
        GroomingReview SavedGroomingReview = groomingReviewPersist.save(groomingReviewToSave);

        //TODO 댕글미터 계산해서 영속

        return ReviewResp.builder()
                .reviewId(SavedGroomingReview.getGroomingReviewId())
                .reviewerId(SavedGroomingReview.getReviewerId())
                .revieweeId(SavedGroomingReview.getGroomerId())
                .build();
    }

    public ReviewResp modifyReview(ModifyGroomingReviewInfo modifyGroomingReviewInfo) {
        GroomingReview savedGroomingReview = groomingReviewPersist.findBy(modifyGroomingReviewInfo.getGroomingReviewId())
                .orElseThrow(() -> new ReviewException(ReviewExceptionType.REVIEW_NOT_FOUND));

        GroomingReview modifiedReview = GroomingReview.modifyBy(savedGroomingReview, modifyGroomingReviewInfo);
        GroomingReview updatedGroomingReview = groomingReviewPersist.save(modifiedReview);

        //TODO 댕글미터 재계산해서 영속

        return ReviewResp.builder()
                .reviewId(updatedGroomingReview.getGroomingReviewId())
                .reviewerId(updatedGroomingReview.getReviewerId())
                .revieweeId(updatedGroomingReview.getGroomerId())
                .build();
    }

    public ReviewResp deleteReview(Long reviewId) {
        GroomingReview savedGroomingReview = groomingReviewPersist.findBy(reviewId)
                .orElseThrow(() -> new ReviewException(ReviewExceptionType.REVIEW_NOT_FOUND));

        groomingReviewPersist.delete(savedGroomingReview);

        //TODO 댕글미터 재계산해서 영속

        return ReviewResp.builder()
                .reviewId(savedGroomingReview.getGroomingReviewId())
                .reviewerId(savedGroomingReview.getReviewerId())
                .revieweeId(savedGroomingReview.getGroomerId())
                .build();
    }

    public List<GroomingReviewSummaryResp> findMyReviewList(Long accountId, int page, int size) {
        User savedUser = userPersist.findBy(accountId)
                .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));

        Pageable pageable = PageRequest.of(page, size);

        Page<GroomingReview> groomingReviews = groomingReviewPersist.findByReviewerId(savedUser.getUserId(), pageable);

        return groomingReviews.stream().map(groomingReview ->
                GroomingReviewSummaryResp.builder()
                        .groomingReviewId(groomingReview.getGroomingReviewId())
                        .groomerId(groomingReview.getGroomerId())
                        .groomingKeywordReviewList(groomingReview.getGroomingKeywordReviewList())
                        .revieweeName(groomingReview.getRevieweeName())
                        .starRating(groomingReview.getStarRating())
                        .content(groomingReview.getContent())
                        .build()
        ).toList();
    }
}
