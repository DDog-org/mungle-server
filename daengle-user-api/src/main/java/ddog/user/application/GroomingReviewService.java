package ddog.user.application;

import ddog.domain.payment.Reservation;
import ddog.domain.review.GroomingReview;
import ddog.domain.review.dto.ModifyGroomingReviewInfo;
import ddog.domain.review.dto.PostGroomingReviewInfo;
import ddog.persistence.mysql.port.GroomingReviewPersist;
import ddog.persistence.mysql.port.ReservationPersist;
import ddog.user.application.exception.ReservationException;
import ddog.user.application.exception.ReservationExceptionType;
import ddog.user.application.exception.ReviewException;
import ddog.user.application.exception.ReviewExceptionType;
import ddog.user.presentation.review.dto.ModifyReviewResp;
import ddog.user.presentation.review.dto.PostReviewResp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroomingReviewService {

    private final GroomingReviewPersist groomingReviewPersist;
    private final ReservationPersist reservationPersist;

    public PostReviewResp postReview(PostGroomingReviewInfo postGroomingReviewInfo) {
        Reservation reservation = reservationPersist.findBy(postGroomingReviewInfo.getReservationId()).orElseThrow(()
                -> new ReservationException(ReservationExceptionType.RESERVATION_NOT_FOUND));

        GroomingReview groomingReviewToSave = GroomingReview.createBy(reservation, postGroomingReviewInfo);
        GroomingReview SavedGroomingReview = groomingReviewPersist.save(groomingReviewToSave);

        //TODO 댕글미터 계산해서 영속

        return PostReviewResp.builder()
                .reviewId(SavedGroomingReview.getGroomingReviewId())
                .reviewerId(SavedGroomingReview.getReviewerId())
                .revieweeId(SavedGroomingReview.getGroomerId())
                .build();
    }

    public ModifyReviewResp modifyReview(ModifyGroomingReviewInfo modifyGroomingReviewInfo) {
        GroomingReview savedGroomingReview = groomingReviewPersist.findBy(modifyGroomingReviewInfo.getGroomingReviewId())
                .orElseThrow(() -> new ReviewException(ReviewExceptionType.REVIEW_NOT_FOUND));

        GroomingReview modifiedReview = GroomingReview.modifyBy(savedGroomingReview, modifyGroomingReviewInfo);
        GroomingReview updatedGroomingReview = groomingReviewPersist.save(modifiedReview);

        //TODO 댕글미터 재계산해서 영속

        return ModifyReviewResp.builder()
                .reviewId(updatedGroomingReview.getGroomingReviewId())
                .reviewerId(updatedGroomingReview.getReviewerId())
                .revieweeId(updatedGroomingReview.getGroomerId())
                .build();
    }
}
