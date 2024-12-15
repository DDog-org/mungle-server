package ddog.domain.review;

import ddog.domain.review.enums.ReportType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportedReview {
    private Long reportedReviewId;
    private Long reporterId;
    private Long reviewerId;
    private ReportType reportType;
    private String reportContent;

    public static void validateReportContent(String content) {
        if (content.length() > 400 || content.length() < 10) {
            throw new IllegalArgumentException("Report Content must be 400 characters or 10 less.");
        }
    }
}
