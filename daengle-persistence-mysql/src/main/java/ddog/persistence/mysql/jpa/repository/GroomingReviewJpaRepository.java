package ddog.persistence.mysql.jpa.repository;

import ddog.persistence.mysql.jpa.entity.GroomingReviewJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroomingReviewJpaRepository extends JpaRepository<GroomingReviewJpaEntity, Long> {
}
