package ddog.user.presentation.review.dto.response;

import ddog.domain.review.enums.CareKeywordReview;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CareReviewSummaryResp {
    private Long careReviewId;
    private Long vetId;
    private String reviewerName;
    private String reviewerImageUrl;
    private List<CareKeywordReview> careKeywordReviewList;
    private String revieweeName;
    private double starRating;
    private String content;
    private List<String> imageUrlList;
}
