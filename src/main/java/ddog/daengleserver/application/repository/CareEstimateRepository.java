package ddog.daengleserver.application.repository;

import ddog.daengleserver.domain.estimate.CareEstimate;

import java.util.List;

public interface CareEstimateRepository {

    void save(CareEstimate careEstimate);

    List<CareEstimate> findCareEstimatesByAddress(String address);

    CareEstimate getByCareEstimateId(Long careEstimateId);

    List<CareEstimate> findCareEstimatesByPetId(Long petId);
}
