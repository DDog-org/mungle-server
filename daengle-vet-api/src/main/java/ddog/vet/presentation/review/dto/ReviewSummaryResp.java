package ddog.vet.presentation.review.dto;

import ddog.domain.vet.enums.CareKeyword;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ReviewSummaryResp {
    private Long careReviewId;
    private Long vetId;
    private List<CareKeyword> careKeywordList;
    private String revieweeName;
    private double starRating;
    private String content;
    private List<String> imageUrlList;
}
