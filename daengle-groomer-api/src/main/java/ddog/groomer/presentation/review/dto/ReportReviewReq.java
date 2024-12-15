package ddog.groomer.presentation.review.dto;

import ddog.domain.review.enums.ReportType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReportReviewReq {
    private Long groomerId;
    private Long reviewId;
    private ReportType reportType;
    private String reportContent;
}
