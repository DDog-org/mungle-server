package ddog.user.application;

import ddog.domain.payment.Reservation;
import ddog.domain.review.CareReview;
import ddog.domain.review.dto.PostCareReviewInfo;
import ddog.persistence.mysql.port.CareReviewPersist;
import ddog.persistence.mysql.port.ReservationPersist;
import ddog.user.application.exception.ReservationException;
import ddog.user.application.exception.ReservationExceptionType;
import ddog.user.presentation.review.dto.PostReviewResp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CareReviewService {

    private final CareReviewPersist careReviewPersist;
    private final ReservationPersist reservationPersist;

    public PostReviewResp postReview(PostCareReviewInfo postCareReviewInfo) {
        Reservation reservation = reservationPersist.findBy(postCareReviewInfo.getReservationId()).orElseThrow(()
                -> new ReservationException(ReservationExceptionType.RESERVATION_NOT_FOUND));

        CareReview careReviewToSave = CareReview.createBy(reservation, postCareReviewInfo);
        CareReview SavedCareReview = careReviewPersist.save(careReviewToSave);

        return PostReviewResp.builder()
                .reviewId(SavedCareReview.getCareReviewId())
                .reviewerId(SavedCareReview.getReviewerId())
                .revieweeId(SavedCareReview.getVetId())
                .build();
    }
}
