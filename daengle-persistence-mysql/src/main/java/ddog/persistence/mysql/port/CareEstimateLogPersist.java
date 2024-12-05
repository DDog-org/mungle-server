package ddog.persistence.port;

import ddog.domain.estimate.CareEstimateLog;

public interface CareEstimateLogPersist {
    void save(CareEstimateLog newEstimateLog);
}
