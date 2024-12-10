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

    public static void validateStarRating(Long starRating) {
        if (starRating == null || starRating < 1 || starRating > 5) {
            throw new IllegalArgumentException("Star rating must be between 1 and 5.");
        }
    }

    public static void validateGroomingKeywordReviewList(List<GroomingKeywordReview> groomingKeywordReviewList) {
        if (groomingKeywordReviewList == null || groomingKeywordReviewList.size() < 1 || groomingKeywordReviewList.size() > 3) {
            throw new IllegalArgumentException("Grooming keyword review list must contain 1 to 3 items.");
        }
    }

    public static void validateContent(String content) {
        if (content != null && content.length() > 400) {
            throw new IllegalArgumentException("Content must be 400 characters or less.");
        }
    }

    public static void validateImageUrlList(List<String> imageUrlList) {
        if (imageUrlList != null && imageUrlList.size() > 10) {
            throw new IllegalArgumentException("Image URL list must contain 10 items or less.");
        }
    }

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
                .shopName(groomingReview.getShopName())
                .starRating(modifyGroomingReviewInfo.getStarRating())
                .content(modifyGroomingReviewInfo.getContent())
                .createTime(groomingReview.getCreateTime())
                .modifiedTime(LocalDateTime.now())
                .imageUrlList(modifyGroomingReviewInfo.getImageUrlList())
                .groomingKeywordReviewList(groomingReview.getGroomingKeywordReviewList())
                .build();
    }
}
