package ddog.persistence.mysql.adapter;

import ddog.domain.estimate.GroomingEstimateLog;
import ddog.persistence.mysql.jpa.repository.GroomingEstimateLogJpaRepository;
import ddog.persistence.mysql.port.GroomingEstimateLogPersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GroomingEstimateLogRepository implements GroomingEstimateLogPersist {

    private final GroomingEstimateLogJpaRepository estimateLogJpaRepository;

    @Override
    public void save(GroomingEstimateLog newEstimateLog) {
        estimateLogJpaRepository.save(ddog.persistence.jpa.entity.GroomingEstimateLogJpaEntity.from(newEstimateLog));
    }
}
