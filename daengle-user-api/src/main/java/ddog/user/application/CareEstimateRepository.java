package ddog.user.application;

import ddog.domain.estimate.CareEstimate;

import java.util.List;

public interface CareEstimateRepository {

    void save(CareEstimate careEstimate);

    CareEstimate getByCareEstimateId(Long careEstimateId);

    List<CareEstimate> findCareEstimatesByPetId(Long petId);

}
