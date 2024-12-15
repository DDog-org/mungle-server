package ddog.domain.review.port;

import ddog.domain.review.ReportedReview;

public interface ReportReviewPersist {
    ReportedReview save(ReportedReview reportedReview);
}
