package ddog.persistence.port;

import ddog.domain.estimate.CareEstimate;

import java.util.List;

public interface CareEstimatePersist {
    void save(CareEstimate careEstimate);
    CareEstimate getByCareEstimateId(Long careEstimateId);
    List<CareEstimate> findCareEstimatesByPetId(Long petId);
}
