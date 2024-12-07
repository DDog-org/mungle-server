package ddog.persistence.mysql.jpa.repository;

import ddog.persistence.mysql.jpa.entity.CareReviewJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CareReviewJpaRepository extends JpaRepository<CareReviewJpaEntity, Long> {

}
