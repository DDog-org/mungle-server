package ddog.domain.review;

import ddog.domain.payment.Reservation;
import ddog.domain.review.dto.ModifyGroomingReviewInfo;
import ddog.domain.review.dto.PostGroomingReviewInfo;
import ddog.domain.review.enums.GroomingKeywordReview;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class GroomingReview extends Review {
    private Long groomingReviewId;
    private Long groomerId;
    private List<GroomingKeywordReview> groomingKeywordReviewList;

    public static GroomingReview createBy(Reservation reservation, PostGroomingReviewInfo postGroomingReviewInfo) {
        return GroomingReview.builder()
                .reservationId(reservation.getReservationId())
                .reviewerId(reservation.getCustomerId())
                .groomerId(reservation.getRecipientId())
                .revieweeName(reservation.getRecipientName())
                .starRating(postGroomingReviewInfo.getStarRating())
                .content(postGroomingReviewInfo.getContent())
                .createTime(LocalDateTime.now())
                .imageUrlList(postGroomingReviewInfo.getImageUrlList())
                .groomerId(reservation.getRecipientId())
                .groomingKeywordReviewList(postGroomingReviewInfo.getGroomingKeywordReviewList())
                .build();
    }

    public static GroomingReview modifyBy(GroomingReview groomingReview, ModifyGroomingReviewInfo modifyGroomingReviewInfo) {
        return GroomingReview.builder()
                .groomingReviewId(groomingReview.getGroomingReviewId())
                .reservationId(groomingReview.getReservationId())
                .reviewerId(groomingReview.getReviewerId())
                .groomerId(groomingReview.getGroomerId())
                .revieweeName(groomingReview.getRevieweeName())
                .starRating(modifyGroomingReviewInfo.getStarRating())
                .content(modifyGroomingReviewInfo.getContent())
                .createTime(groomingReview.getCreateTime())
                .modifiedTime(LocalDateTime.now())
                .imageUrlList(modifyGroomingReviewInfo.getImageUrlList())
                .groomerId(groomingReview.getGroomerId())
                .groomingKeywordReviewList(groomingReview.getGroomingKeywordReviewList())
                .build();
    }
}
