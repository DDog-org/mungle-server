package ddog.domain.review;

import ddog.domain.review.enums.CareKeywordReview;
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
    List<CareKeywordReview> careKeywordReviewList;
}
