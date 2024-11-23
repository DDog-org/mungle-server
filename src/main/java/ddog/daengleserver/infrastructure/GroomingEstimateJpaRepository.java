package ddog.daengleserver.infrastructure;

import ddog.daengleserver.infrastructure.jpa.GroomingEstimateJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroomingEstimateJpaRepository extends JpaRepository<GroomingEstimateJpaEntity, Long> {

    List<GroomingEstimateJpaEntity> findGroomingEstimatesJpaEntitiesByAddress(String address);

}
