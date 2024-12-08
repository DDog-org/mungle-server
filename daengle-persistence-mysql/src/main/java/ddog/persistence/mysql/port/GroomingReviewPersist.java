package ddog.persistence.mysql.port;

import ddog.domain.review.CareReview;
import ddog.domain.review.GroomingReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface GroomingReviewPersist {
    Optional<GroomingReview> findBy(Long groomingId);
    GroomingReview save(GroomingReview groomingReview);
    void delete(GroomingReview groomingReview);
    Page<GroomingReview> findByReviewerId(Long userId, Pageable pageable);
    Page<GroomingReview> findByGroomerId(Long userId, Pageable pageable);
}
