package ddog.persistence.adapter;

import ddog.persistence.jpa.repository.GroomingEstimateLogJpaRepository;
import ddog.persistence.port.GroomingEstimateLogPersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GroomingEstimateLogRepository implements GroomingEstimateLogPersist {

    private final GroomingEstimateLogJpaRepository estimateLogJpaRepository;


}
