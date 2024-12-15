package ddog.groomer.application.mapper;


import ddog.domain.review.GroomingReview;
import ddog.domain.review.ReportedReview;
import ddog.groomer.presentation.review.dto.ReportReviewReq;


public class ReportReviewMapper {

    public static ReportedReview create(GroomingReview groomingReview, ReportReviewReq reportReviewReq) {
        return ReportedReview.builder()
                .reportedReviewId(reportReviewReq.getReviewId())
                .reporterId(reportReviewReq.getGroomerId())
                .reviewerId(groomingReview.getReviewerId())
                .reportType(reportReviewReq.getReportType())
                .reportContent(reportReviewReq.getReportContent())
                .build();
    }
}
