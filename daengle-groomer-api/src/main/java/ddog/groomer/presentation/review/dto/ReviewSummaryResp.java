package ddog.groomer.presentation.review.dto;

import ddog.domain.review.enums.GroomingKeywordReview;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ReviewSummaryResp {
    private Long groomingReviewId;
    private Long groomerId;
    private List<GroomingKeywordReview> groomingKeywordReviewList;
    private String revieweeName;
    private double starRating;
    private String content;
    private List<String> imageUrlList;
}
