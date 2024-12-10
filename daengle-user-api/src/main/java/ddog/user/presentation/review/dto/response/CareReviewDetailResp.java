package ddog.user.presentation.review.dto.response;

import ddog.domain.review.enums.CareKeywordReview;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CareReviewDetailResp {
    private Long careReviewId;
    private Long vetId;
    private List<CareKeywordReview> careKeywordReviewList;
    private String revieweeName;
    private String shopName;
    private double starRating;
    private String content;
    private List<String> imageUrlList;
}
