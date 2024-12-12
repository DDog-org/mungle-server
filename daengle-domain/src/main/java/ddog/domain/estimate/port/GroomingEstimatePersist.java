package ddog.domain.estimate.port;

import ddog.domain.estimate.EstimateStatus;
import ddog.domain.estimate.GroomingEstimate;
import ddog.domain.estimate.Proposal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface GroomingEstimatePersist {
    GroomingEstimate save(GroomingEstimate groomingEstimate);

    Optional<GroomingEstimate> findByEstimateId(Long groomingEstimateId);

    List<GroomingEstimate> findGroomingEstimatesByAddress(String address);

    List<GroomingEstimate> findGroomingEstimatesByGroomerId(Long groomerId);

    List<GroomingEstimate> findByPetIdAndStatusAndPageable(Long petId);

    void updateStatusWithParentId(EstimateStatus estimateStatus, Long parentId);

    Page<GroomingEstimate> findByPetIdAndStatusAndProposal(Long petId, EstimateStatus status, Proposal proposal, Pageable pageable);

    boolean hasGeneralEstimateByPetId(Long petId);

    boolean hasDesignationEstimateByPetId(Long petId);
}
