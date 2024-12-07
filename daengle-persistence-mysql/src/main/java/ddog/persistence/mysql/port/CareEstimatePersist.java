package ddog.persistence.mysql.port;

import ddog.domain.estimate.CareEstimate;

import java.util.List;
import java.util.Optional;

public interface CareEstimatePersist {
    CareEstimate save(CareEstimate careEstimate);

    Optional<CareEstimate> findByEstimateId(Long careEstimateId);

    List<CareEstimate> findCareEstimatesByPetId(Long petId);

    List<CareEstimate> findCareEstimatesByAddress(String address);

    List<CareEstimate> findCareEstimatesByVetId(Long vetId);
}
