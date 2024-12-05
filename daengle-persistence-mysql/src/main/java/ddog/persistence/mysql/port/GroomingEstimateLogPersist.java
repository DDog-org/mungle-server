package ddog.persistence.port;

import ddog.domain.estimate.GroomingEstimateLog;

public interface GroomingEstimateLogPersist {
    void save(GroomingEstimateLog newEstimateLog);
}
