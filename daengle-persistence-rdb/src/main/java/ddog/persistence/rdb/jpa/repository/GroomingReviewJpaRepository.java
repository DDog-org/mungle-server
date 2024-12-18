package ddog.persistence.rdb.jpa.repository;

import ddog.persistence.rdb.jpa.entity.GroomingReviewJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroomingReviewJpaRepository extends JpaRepository<GroomingReviewJpaEntity, Long> {
    Optional<GroomingReviewJpaEntity> findByReservationId(Long reservationId);
    Optional<GroomingReviewJpaEntity> findByReviewerIdAndReservationId(Long reviewerId, Long reservationId);
    Page<GroomingReviewJpaEntity> findByReviewerId(Long reviewerId, Pageable pageable);
    Page<GroomingReviewJpaEntity> findByRevieweeId(Long reviewerId, Pageable pageable);
    Page<GroomingReviewJpaEntity> findByGroomerId(Long groomerId, Pageable pageable);
}
