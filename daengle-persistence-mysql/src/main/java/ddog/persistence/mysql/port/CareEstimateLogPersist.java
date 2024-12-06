package ddog.persistence.mysql.port;

import ddog.domain.estimate.CareEstimateLog;

public interface CareEstimateLogPersist {
    void save(CareEstimateLog newEstimateLog);
}
