package ddog.domain.review.port;

import ddog.domain.review.GroomingReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface GroomingReviewPersist {
    Optional<GroomingReview> findByReviewId(Long groomingReviewId);
    GroomingReview save(GroomingReview groomingReview);
    void delete(GroomingReview groomingReview);
    Optional<GroomingReview> findByReviewerIdAndReservationId(Long reviewerId, Long reservationId);
    Page<GroomingReview> findByReviewerId(Long userId, Pageable pageable);
    Page<GroomingReview> findByGroomerId(Long userId, Pageable pageable);
}
