package ddog.persistence.mysql.jpa.repository;

import ddog.persistence.mysql.jpa.entity.ReportedReviewJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportedReviewJpaRepository extends JpaRepository<ReportedReviewJpaEntity, Long> {
    ReportedReviewJpaEntity save(ReportedReviewJpaEntity reportedReviewJpaEntity);
    Page<ReportedReviewJpaEntity> findByReporterId(Long vetId, Pageable pageable);
}
