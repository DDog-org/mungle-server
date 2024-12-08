package ddog.domain.review.dto;

import ddog.domain.review.enums.GroomingKeywordReview;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ModifyGroomingReviewInfo {
    private Long starRating;
    private List<GroomingKeywordReview> groomingKeywordReviewList;
    private String content;
    private List<String> imageUrlList;
}
