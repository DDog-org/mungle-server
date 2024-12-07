package ddog.user.presentation.review.dto;

import ddog.domain.review.enums.CareKeywordReview;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ReviewSummaryResp {
    private Long careReviewId;
    private Long vetId;
    private List<CareKeywordReview> careKeywordReviewList;
    private String revieweeName;
    private double starRating;
    private String content;
}
