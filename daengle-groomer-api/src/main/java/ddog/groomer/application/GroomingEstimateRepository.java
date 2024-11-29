package ddog.groomer.application;

import ddog.daengleserver.domain.estimate.GroomingEstimate;

import java.util.List;

public interface GroomingEstimateRepository {

    void save(GroomingEstimate groomingEstimate);

    GroomingEstimate getByGroomingEstimateId(Long groomingEstimateId);

    List<GroomingEstimate> findGroomingEstimatesByPetId(Long petId);

    List<GroomingEstimate> findGeneralGroomingEstimates(String address);

    List<GroomingEstimate> findDesignationGroomingEstimates(Long groomerId);
}
