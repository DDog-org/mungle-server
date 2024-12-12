package ddog.persistence.mysql.jpa.repository;

import ddog.persistence.mysql.jpa.entity.CareReviewJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CareReviewJpaRepository extends JpaRepository<CareReviewJpaEntity, Long> {
    Page<CareReviewJpaEntity> findByReviewerId(Long reviewerId, Pageable pageable);
    Page<CareReviewJpaEntity> findByRevieweeId(Long reviewerId, Pageable pageable);
    Page<CareReviewJpaEntity> findByVetId(Long vetId, Pageable pageable);
}
