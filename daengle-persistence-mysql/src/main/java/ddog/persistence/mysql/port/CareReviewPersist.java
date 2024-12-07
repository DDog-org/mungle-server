package ddog.persistence.mysql.port;

import ddog.domain.review.CareReview;

import java.util.List;
import java.util.Optional;

public interface CareReviewPersist {
    Optional<CareReview> findBy(Long careReviewId);
    CareReview save(CareReview careReview);
    void delete(CareReview careReview);
    List<CareReview> findByReviewerId(Long reviewerId);
}
