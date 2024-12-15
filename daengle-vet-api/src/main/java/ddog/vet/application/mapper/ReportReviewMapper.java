package ddog.vet.application.mapper;

import ddog.domain.review.CareReview;
import ddog.domain.review.ReportedReview;
import ddog.vet.presentation.review.dto.ReportReviewReq;

public class ReportReviewMapper {

    public static ReportedReview create(CareReview careReview, ReportReviewReq reportReviewReq) {
        return ReportedReview.builder()
                .reportedReviewId(reportReviewReq.getReviewId())
                .reporterId(reportReviewReq.getVetId())
                .reviewerId(careReview.getReviewerId())
                .reportType(reportReviewReq.getReportType())
                .reportContent(reportReviewReq.getReportContent())
                .build();
    }
}
