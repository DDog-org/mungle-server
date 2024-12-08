package ddog.domain.review;

import ddog.domain.payment.Reservation;
import ddog.domain.review.dto.ModifyCareReviewInfo;
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
                .revieweeName(reservation.getRecipientName())
                .starRating(postCareReviewInfo.getStarRating())
                .content(postCareReviewInfo.getContent())
                .createTime(LocalDateTime.now())
                .imageUrlList(postCareReviewInfo.getImageUrlList())
                .vetId(reservation.getRecipientId())
                .careKeywordReviewList(postCareReviewInfo.getCareKeywordReviewList())
                .build();
    }

    public static CareReview modifyBy(CareReview careReview, ModifyCareReviewInfo modifyCareReviewInfo) {
        return CareReview.builder()
                .careReviewId(careReview.getCareReviewId())
                .reservationId(careReview.getReservationId())
                .reviewerId(careReview.getReviewerId())
                .vetId(careReview.getVetId())
                .revieweeName(careReview.getRevieweeName())
                .starRating(modifyCareReviewInfo.getStarRating())
                .content(modifyCareReviewInfo.getContent())
                .createTime(careReview.getCreateTime())
                .modifiedTime(LocalDateTime.now())
                .imageUrlList(modifyCareReviewInfo.getImageUrlList())
                .careKeywordReviewList(modifyCareReviewInfo.getCareKeywordReviewList())
                .build();
    }
}
