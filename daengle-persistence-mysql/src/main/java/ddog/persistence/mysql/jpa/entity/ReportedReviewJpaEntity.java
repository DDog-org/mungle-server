package ddog.persistence.mysql.jpa.entity;

import ddog.domain.review.ReportedReview;
import ddog.domain.review.enums.ReportType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ReportedReviews")
public class ReportedReviewJpaEntity {
    @Id
    private Long reportedReviewId; //groomingId or careReviewId
    private Long reporterId;
    private Long reviewerId;

    @Enumerated(EnumType.STRING)
    private ReportType reportType;

    private String reportContent;

    public ReportedReview toModel() {
        return ReportedReview.builder()
                .reportedReviewId(reportedReviewId)
                .reporterId(reporterId)
                .reviewerId(reviewerId)
                .reportType(reportType)
                .reportContent(reportContent)
                .build();
    }

    public static ReportedReviewJpaEntity from(ReportedReview reportedReview) {
        return ReportedReviewJpaEntity.builder()
                .reportedReviewId(reportedReview.getReportedReviewId())
                .reporterId(reportedReview.getReporterId())
                .reviewerId(reportedReview.getReviewerId())
                .reportType(reportedReview.getReportType())
                .reportContent(reportedReview.getReportContent())
                .build();
    }
}
