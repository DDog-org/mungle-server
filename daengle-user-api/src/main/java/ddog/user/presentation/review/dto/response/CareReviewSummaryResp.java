package ddog.user.presentation.review.dto.response;

import ddog.domain.vet.enums.CareKeyword;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class CareReviewSummaryResp {
    private Long careReviewId;
    private Long vetId;
    private List<CareKeyword> careKeywordList;
    private String reviewerName;
    private String reviewerImageUrl;
    private String revieweeName;
    private LocalDateTime createdAt;
    private double starRating;
    private String content;
    private List<String> imageUrlList;
}
