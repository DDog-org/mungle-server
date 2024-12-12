package ddog.domain.review;

import ddog.domain.groomer.enums.GroomingKeyword;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class GroomingReview extends Review {
    private Long groomingReviewId;
    private Long groomerId;
    private List<GroomingKeyword> groomingKeywordList;

    public static void validateGroomingKeywordReviewList(List<GroomingKeyword> groomingKeywordList) {
        if (groomingKeywordList == null || groomingKeywordList.isEmpty() || groomingKeywordList.size() > 5) {
            throw new IllegalArgumentException("Grooming keyword review list must contain 1 to 3 items.");
        }
    }
}
