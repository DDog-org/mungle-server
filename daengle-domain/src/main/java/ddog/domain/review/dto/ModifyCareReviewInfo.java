package ddog.domain.review.dto;

import ddog.domain.review.enums.CareKeywordReview;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ModifyCareReviewInfo {
    private Long starRating;
    private List<CareKeywordReview> careKeywordReviewList;
    private String content;
    private List<String> imageUrlList;
}
