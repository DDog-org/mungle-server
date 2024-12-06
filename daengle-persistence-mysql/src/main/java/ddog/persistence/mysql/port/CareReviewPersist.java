package ddog.persistence.mysql.port;

import ddog.domain.review.CareReview;

public interface CareReviewPersist {
    CareReview save(CareReview careReview);
}
