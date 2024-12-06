package ddog.persistence.mysql.port;

import ddog.domain.review.CareReview;

import java.util.Optional;

public interface CareReviewPersist {
    Optional<CareReview> findBy(Long careReviewId);
    CareReview save(CareReview careReview);
}
