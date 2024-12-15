package ddog.groomer.presentation.review.dto;

import ddog.domain.groomer.enums.GroomingKeyword;
import ddog.domain.review.enums.ReportType;
import ddog.domain.vet.enums.CareKeyword;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ReportedReviewSummaryResp {
    private Long groomingReviewId;
    private Long groomerId;
    private List<GroomingKeyword> groomingKeywordList;
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
