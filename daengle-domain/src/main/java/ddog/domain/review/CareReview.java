package ddog.domain.review;

import ddog.domain.payment.Reservation;
import ddog.domain.review.dto.PostCareReviewInfo;
import ddog.domain.review.enums.CareKeywordReview;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CareReview extends Review {
    private Long careReviewId;
    private Long vetId;
    private List<CareKeywordReview> careKeywordReviewList;

    public static CareReview createBy(Reservation reservation, PostCareReviewInfo postCareReviewInfo) {
        return CareReview.builder()
                .reservationId(reservation.getReservationId())
                .reviewerId(reservation.getCustomerId())
                .vetId(reservation.getRecipientId())
                .revieweeName(reservation.getRecipientName())
                .starRating(postCareReviewInfo.getStarRating())
                .content(postCareReviewInfo.getContent())
                .createTime(LocalDateTime.now())
                .imageUrlList(postCareReviewInfo.getImageUrlList())
                .build();
    }
}
