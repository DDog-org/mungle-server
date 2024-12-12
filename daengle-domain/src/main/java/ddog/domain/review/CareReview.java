package ddog.domain.review;

import ddog.domain.vet.enums.CareKeyword;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CareReview extends Review {
    private Long careReviewId;
    private Long vetId;
    private List<CareKeyword> careKeywordList;

    public static void validateCareKeywordReviewList(List<CareKeyword> careKeywordList) {
        if (careKeywordList == null || careKeywordList.isEmpty() || careKeywordList.size() > 5) {
            throw new IllegalArgumentException("Care keyword review list must contain 1 to 3 items.");
        }
    }
}
