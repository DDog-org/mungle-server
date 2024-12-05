package ddog.persistence.mysql.port;

import ddog.domain.estimate.GroomingEstimate;

import java.util.List;

public interface GroomingEstimatePersist {
    void save(GroomingEstimate groomingEstimate);
    GroomingEstimate getByGroomingEstimateId(Long groomingEstimateId);
    List<GroomingEstimate> findGeneralGroomingEstimates(String address);
    List<GroomingEstimate> findDesignationGroomingEstimates(Long groomerId);
    List<GroomingEstimate> findGroomingEstimatesByPetId(Long petId);
}
