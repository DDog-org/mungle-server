package ddog.persistence.adapter;

import ddog.persistence.jpa.repository.CareEstimateLogJpaRepository;
import ddog.persistence.port.CareEstimateLogPersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CareEstimateLogRepository implements CareEstimateLogPersist {

    private final CareEstimateLogJpaRepository careEstimateLogJpaRepository;


}
