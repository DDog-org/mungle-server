package ddog.persistence.mysql.jpa.repository;

import ddog.persistence.mysql.jpa.entity.GroomingReviewJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroomingReviewJpaRepository extends JpaRepository<GroomingReviewJpaEntity, Long> {
    Page<GroomingReviewJpaEntity> findByReviewerId(Long reviewerId, Pageable pageable);
    Page<GroomingReviewJpaEntity> findByGroomerId(Long groomerId, Pageable pageable);
}
