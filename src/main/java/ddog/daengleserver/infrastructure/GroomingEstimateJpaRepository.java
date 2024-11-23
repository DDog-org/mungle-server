package ddog.daengleserver.infrastructure;

import ddog.daengleserver.infrastructure.po.GroomingEstimateJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroomingEstimateJpaRepository extends JpaRepository<GroomingEstimateJpaEntity, Long> {

}
