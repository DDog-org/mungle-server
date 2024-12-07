package ddog.persistence.mysql.port;

import ddog.domain.estimate.GroomingEstimate;

import java.util.List;
import java.util.Optional;

public interface GroomingEstimatePersist {
    GroomingEstimate save(GroomingEstimate groomingEstimate);

    Optional<GroomingEstimate> findByEstimateId(Long groomingEstimateId);

    List<GroomingEstimate> findGeneralGroomingEstimates(String address);

    List<GroomingEstimate> findDesignationGroomingEstimates(Long groomerId);

    List<GroomingEstimate> findGroomingEstimatesByPetId(Long petId);
}
