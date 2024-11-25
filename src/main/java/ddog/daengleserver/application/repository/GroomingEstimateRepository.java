package ddog.daengleserver.application.repository;

import ddog.daengleserver.domain.estimate.GroomingEstimate;

import java.util.List;

public interface GroomingEstimateRepository {

    void save(GroomingEstimate groomingEstimate);

    List<GroomingEstimate> findGroomingEstimatesByAddress(String address);

    GroomingEstimate getByGroomingEstimateId(Long groomingEstimateId);

    List<GroomingEstimate> findGroomingEstimatesByPetId(Long petId);
}
