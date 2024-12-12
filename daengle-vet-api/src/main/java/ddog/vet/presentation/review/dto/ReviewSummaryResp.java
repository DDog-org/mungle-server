package ddog.vet.presentation.review.dto;

import ddog.domain.vet.enums.CareKeyword;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ReviewSummaryResp {
    private Long careReviewId;
    private Long vetId;
    private List<CareKeyword> careKeywordList;
    private String reviewerName;
    private String reviewerImageUrl;
    private String revieweeName;
    private LocalDateTime schedule;
    private double starRating;
    private String content;
    private List<String> imageUrlList;
}
