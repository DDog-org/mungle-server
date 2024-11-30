package ddog.user.application;


import ddog.domain.estimate.GroomingEstimate;

import java.util.List;

public interface GroomingEstimateRepository {

    void save(GroomingEstimate groomingEstimate);

    GroomingEstimate getByGroomingEstimateId(Long groomingEstimateId);

    List<GroomingEstimate> findGroomingEstimatesByPetId(Long petId);
}
