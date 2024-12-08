package ddog.persistence.mysql.port;

import ddog.domain.review.CareReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CareReviewPersist {
    Optional<CareReview> findBy(Long careReviewId);
    CareReview save(CareReview careReview);
    void delete(CareReview careReview);
    Page<CareReview> findByReviewerId(Long reviewerId, Pageable pageable);
}
