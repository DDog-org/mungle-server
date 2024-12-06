package ddog.persistence.mysql.port;

import ddog.domain.review.GroomingReview;

import java.util.Optional;

public interface GroomingReviewPersist {
    Optional<GroomingReview> findBy(Long groomingId);
    GroomingReview save(GroomingReview groomingReview);
    void delete(GroomingReview groomingReview);
}
