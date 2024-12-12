package ddog.user.application.mapper;

import ddog.domain.payment.Reservation;
import ddog.domain.review.GroomingReview;
import ddog.user.presentation.review.dto.request.UpdateGroomingReviewInfo;
import ddog.user.presentation.review.dto.request.PostGroomingReviewInfo;

import java.time.LocalDateTime;

public class GroomingReviewMapper {

    public static GroomingReview createBy(Reservation reservation, PostGroomingReviewInfo postGroomingReviewInfo) {
        return GroomingReview.builder()
                .reservationId(reservation.getReservationId())
                .reviewerId(reservation.getCustomerId())
                .groomerId(reservation.getRecipientId())
                .revieweeName(reservation.getRecipientName())
                .shopName(reservation.getShopName())
                .starRating(postGroomingReviewInfo.getStarRating())
                .content(postGroomingReviewInfo.getContent())
                .createTime(LocalDateTime.now())
                .imageUrlList(postGroomingReviewInfo.getImageUrlList())
                .groomingKeywordList(postGroomingReviewInfo.getGroomingKeywordList())
                .build();
    }

    public static GroomingReview updateBy(GroomingReview groomingReview, UpdateGroomingReviewInfo updateGroomingReviewInfo) {
        return GroomingReview.builder()
                .groomingReviewId(groomingReview.getGroomingReviewId())
                .reservationId(groomingReview.getReservationId())
                .reviewerId(groomingReview.getReviewerId())
                .groomerId(groomingReview.getGroomerId())
                .revieweeName(groomingReview.getRevieweeName())
                .shopName(groomingReview.getShopName())
                .starRating(updateGroomingReviewInfo.getStarRating())
                .content(updateGroomingReviewInfo.getContent())
                .createTime(groomingReview.getCreateTime())
                .modifiedTime(LocalDateTime.now())
                .imageUrlList(updateGroomingReviewInfo.getImageUrlList())
                .groomingKeywordList(updateGroomingReviewInfo.getGroomingKeywordList())
                .build();
    }
}
