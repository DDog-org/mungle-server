package ddog.domain.review.port;

import ddog.domain.review.ReportedReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReportReviewPersist {
    ReportedReview save(ReportedReview reportedReview);

    Page<ReportedReview> findByReporterId(Long vetId, Pageable pageable);
}
