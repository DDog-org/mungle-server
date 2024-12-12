package ddog.groomer.presentation.review.dto;

import ddog.domain.groomer.enums.GroomingKeyword;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ReviewSummaryResp {
    private Long groomingReviewId;
    private Long groomerId;
    private List<GroomingKeyword> groomingKeywordList;
    private String revieweeName;
    private double starRating;
    private String content;
    private List<String> imageUrlList;
}
