package ddog.domain.review;

import ddog.domain.review.enums.GroomingKeywordReview;
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
public class GroomingReview extends Review {
    private Long groomingReviewId;
    private Long groomerId;
    private List<GroomingKeywordReview> groomingKeywordReviewList;

    public static void validateGroomingKeywordReviewList(List<GroomingKeywordReview> groomingKeywordReviewList) {
        if (groomingKeywordReviewList == null || groomingKeywordReviewList.isEmpty() || groomingKeywordReviewList.size() > 3) {
            throw new IllegalArgumentException("Grooming keyword review list must contain 1 to 3 items.");
        }
    }
}
