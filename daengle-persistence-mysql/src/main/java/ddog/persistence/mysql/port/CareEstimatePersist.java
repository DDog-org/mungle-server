package ddog.persistence.mysql.port;

import ddog.domain.estimate.CareEstimate;

import java.util.List;
import java.util.Optional;

public interface CareEstimatePersist {
    CareEstimate save(CareEstimate careEstimate);

    Optional<CareEstimate> findByEstimateId(Long careEstimateId);

    List<CareEstimate> findCareEstimatesByPetId(Long petId);

    List<CareEstimate> findGeneralCareEstimates(String address);

    List<CareEstimate> findDesignationCareEstimates(Long vetId);
}
