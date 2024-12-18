package ddog.persistence.rdb.jpa.repository;

import ddog.persistence.rdb.jpa.entity.CareReviewJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CareReviewJpaRepository extends JpaRepository<CareReviewJpaEntity, Long> {
    Optional<CareReviewJpaEntity> findByReservationId(Long reservationId);
    Optional<CareReviewJpaEntity> findByReviewerIdAndReservationId(Long reviewerId, Long reservationId);
    Page<CareReviewJpaEntity> findByReviewerId(Long reviewerId, Pageable pageable);
    Page<CareReviewJpaEntity> findByRevieweeId(Long reviewerId, Pageable pageable);
    Page<CareReviewJpaEntity> findByVetId(Long vetId, Pageable pageable);
}
