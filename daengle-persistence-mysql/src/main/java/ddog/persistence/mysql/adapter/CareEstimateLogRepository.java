package ddog.persistence.mysql.adapter;

import ddog.domain.estimate.CareEstimateLog;
import ddog.persistence.mysql.jpa.entity.CareEstimateLogJpaEntity;
import ddog.persistence.mysql.jpa.repository.CareEstimateLogJpaRepository;
import ddog.persistence.mysql.port.CareEstimateLogPersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CareEstimateLogRepository implements CareEstimateLogPersist {

    private final CareEstimateLogJpaRepository careEstimateLogJpaRepository;


    @Override
    public void save(CareEstimateLog newEstimateLog) {
        careEstimateLogJpaRepository.save(CareEstimateLogJpaEntity.from(newEstimateLog));
    }
}
