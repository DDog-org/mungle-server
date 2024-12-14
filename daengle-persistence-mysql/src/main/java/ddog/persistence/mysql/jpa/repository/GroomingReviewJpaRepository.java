package ddog.persistence.mysql.jpa.repository;

import ddog.persistence.mysql.jpa.entity.GroomingReviewJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroomingReviewJpaRepository extends JpaRepository<GroomingReviewJpaEntity, Long> {
    Optional<GroomingReviewJpaEntity> findByReviewerIdAndReservationId(Long reviewerId, Long reservationId);
    Page<GroomingReviewJpaEntity> findByReviewerId(Long reviewerId, Pageable pageable);
    Page<GroomingReviewJpaEntity> findByGroomerId(Long groomerId, Pageable pageable);
}
