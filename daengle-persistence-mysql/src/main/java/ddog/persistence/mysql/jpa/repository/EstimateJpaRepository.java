package ddog.persistence.jpa.repository;

import ddog.persistence.jpa.entity.EstimateJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstimateJpaRepository extends JpaRepository<EstimateJpaEntity, Long> {

}
