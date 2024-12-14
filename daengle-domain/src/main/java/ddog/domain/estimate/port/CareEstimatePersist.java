package ddog.domain.estimate.port;

import ddog.domain.estimate.CareEstimate;
import ddog.domain.estimate.EstimateStatus;
import ddog.domain.estimate.Proposal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CareEstimatePersist {
    CareEstimate save(CareEstimate careEstimate);

    Optional<CareEstimate> findByEstimateId(Long careEstimateId);

    void updateStatusWithParentId(EstimateStatus estimateStatus, Long parentId);

    Page<CareEstimate> findByPetIdAndStatusAndProposal(Long petId, EstimateStatus status, Proposal proposal, Pageable pageable);

    Optional<CareEstimate> findByEstimateStatusAndProposalAndPetId(EstimateStatus estimateStatus, Proposal proposal, Long petId);

    Page<CareEstimate> findByStatusAndProposalAndAddress(EstimateStatus estimateStatus, Proposal proposal, String address, Pageable pageable);

    Page<CareEstimate> findByStatusAndProposalAndVetId(EstimateStatus estimateStatus, Proposal proposal, Long accountId, Pageable pageable);

    List<CareEstimate> findCareEstimatesByVetIdAndEstimateStatus(Long vetAccountId);

    List<CareEstimate> findCareEstimatesByVetIdAndProposal(Long vetAccountId);

    List<CareEstimate> findCareEstimatesByVetId(Long vetAccountId);

    List<CareEstimate> findTodayCareSchedule(Long vetAccountId, LocalDate dateTime, EstimateStatus estimateStatus);

    List<CareEstimate> findMyEstimatesByUserId(Long userId);
}
