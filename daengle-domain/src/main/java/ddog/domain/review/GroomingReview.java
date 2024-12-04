package ddog.domain.review;

import ddog.domain.review.enums.GroomingKeywordReview;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class GroomingReview extends Review {
    List<GroomingKeywordReview> groomingKeywordReviewList;
}
