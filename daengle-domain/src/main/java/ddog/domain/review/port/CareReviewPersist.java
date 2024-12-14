package ddog.domain.review.port;

import ddog.domain.review.CareReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CareReviewPersist {
    Optional<CareReview> findByReviewId(Long careReviewId);
    CareReview save(CareReview careReview);
    void delete(CareReview careReview);
    Optional<CareReview> findByReservationId(Long reservationId);
    Optional<CareReview> findByReviewerIdAndReservationId(Long reviewerId, Long reservationId);
    Page<CareReview> findByReviewerId(Long userId, Pageable pageable);
    Page<CareReview> findByRevieweeId(Long userId, Pageable pageable);
    Page<CareReview> findByVetId(Long reviewerId, Pageable pageable);
}
