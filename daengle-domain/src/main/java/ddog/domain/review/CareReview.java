package ddog.domain.review;

import ddog.domain.payment.Reservation;
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

    public static void validateCareKeywordReviewList(List<CareKeywordReview> careKeywordReviewList) {
        if (careKeywordReviewList == null || careKeywordReviewList.isEmpty() || careKeywordReviewList.size() > 5) {
            throw new IllegalArgumentException("Care keyword review list must contain 1 to 3 items.");
        }
    }
}
