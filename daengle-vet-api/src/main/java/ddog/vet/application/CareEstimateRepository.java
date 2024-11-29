package ddog.vet.application;

import ddog.daengleserver.domain.estimate.CareEstimate;

import java.util.List;

public interface CareEstimateRepository {

    void save(CareEstimate careEstimate);

    CareEstimate getByCareEstimateId(Long careEstimateId);

    List<CareEstimate> findCareEstimatesByPetId(Long petId);

    List<CareEstimate> findGeneralCareEstimates(String address);

    List<CareEstimate> findDesignationCareEstimates(Long vetId);
}
