package ddog.vet.presentation.review.dto;

import ddog.domain.review.enums.ReportType;
import ddog.domain.vet.enums.CareKeyword;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ReportedReviewSummaryResp {
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
    private ReportType reportType;
    private String reportContent;
}
