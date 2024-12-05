package ddog.persistence.mysql.jpa.repository;

import ddog.persistence.mysql.jpa.entity.CareEstimateLogJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CareEstimateLogJpaRepository extends JpaRepository<CareEstimateLogJpaEntity, Long> {

}
