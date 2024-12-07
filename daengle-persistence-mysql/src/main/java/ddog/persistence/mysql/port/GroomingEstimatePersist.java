package ddog.persistence.mysql.port;

import ddog.domain.estimate.GroomingEstimate;

import java.util.List;

public interface GroomingEstimatePersist {
    GroomingEstimate save(GroomingEstimate groomingEstimate);
    GroomingEstimate getByEstimateId(Long groomingEstimateId);
    List<GroomingEstimate> findGeneralGroomingEstimates(String address);
    List<GroomingEstimate> findDesignationGroomingEstimates(Long groomerId);
    List<GroomingEstimate> findGroomingEstimatesByPetId(Long petId);
}
