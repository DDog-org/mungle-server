package ddog.persistence.mysql.port;

import ddog.domain.estimate.CareEstimate;

import java.util.List;

public interface CareEstimatePersist {
    void save(CareEstimate careEstimate);
    CareEstimate getByCareEstimateId(Long careEstimateId);
    List<CareEstimate> findCareEstimatesByPetId(Long petId);
    List<CareEstimate> findGeneralCareEstimates(String address);
    List<CareEstimate> findDesignationCareEstimates(Long vetId);
}
