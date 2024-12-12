package ddog.persistence.mysql.adapter;

import ddog.domain.estimate.CareEstimate;
import ddog.domain.estimate.EstimateStatus;
import ddog.domain.estimate.Proposal;
import ddog.domain.estimate.port.CareEstimatePersist;
import ddog.persistence.mysql.jpa.entity.CareEstimateJpaEntity;
import ddog.persistence.mysql.jpa.repository.CareEstimateJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CareEstimateRepository implements CareEstimatePersist {

    private final CareEstimateJpaRepository careEstimateJpaRepository;

    @Override
    public CareEstimate save(CareEstimate careEstimate) {
        return careEstimateJpaRepository.save(CareEstimateJpaEntity.from(careEstimate))
                .toModel();
    }

    @Override
    public void updateStatusWithParentId(EstimateStatus estimateStatus, Long parentId) {
        careEstimateJpaRepository.updateParentEstimateByParentId(estimateStatus, parentId);
        careEstimateJpaRepository.updateStatusByParentId(estimateStatus, parentId);
    }

    @Override
    public Page<CareEstimate> findByPetIdAndStatusAndProposal(Long petId, EstimateStatus status, Proposal proposal, Pageable pageable) {
        return careEstimateJpaRepository.findByPetIdAndStatusAndProposal(petId, status, proposal, pageable)
                .map(CareEstimateJpaEntity::toModel);
    }

    @Override
    public Optional<CareEstimate> findByEstimateStatusAndProposalAndPetId(EstimateStatus estimateStatus, Proposal proposal, Long petId) {
        return careEstimateJpaRepository.findTopByStatusAndProposalAndPetId(estimateStatus, proposal, petId)
                .map(CareEstimateJpaEntity::toModel);
    }

    @Override
    public Page<CareEstimate> findByStatusAndProposalAndAddress(EstimateStatus status, Proposal proposal, String address, Pageable pageable) {
        return careEstimateJpaRepository.findByStatusAndProposalAndAddress(status, proposal, address, pageable)
                .map(CareEstimateJpaEntity::toModel);
    }

    @Override
    public Page<CareEstimate> findByStatusAndProposalAndVetId(EstimateStatus status, Proposal proposal, Long accountId, Pageable pageable) {
        return careEstimateJpaRepository.findByStatusAndProposalAndVetId(status, proposal, accountId, pageable)
                .map(CareEstimateJpaEntity::toModel);
    }

    @Override
    public Optional<CareEstimate> findByEstimateId(Long estimateId) {
        return careEstimateJpaRepository.findByEstimateId(estimateId)
                .map(CareEstimateJpaEntity::toModel);
    }
    public List<CareEstimate> findCareEstimatesByVetIdAndEstimateStatus(Long vetId) {
        List<CareEstimate> estimate = new ArrayList<>();
        List<CareEstimateJpaEntity> findEstimates = careEstimateJpaRepository.findCareEstimatesByVetIdAndStatus(vetId, EstimateStatus.ON_RESERVATION);

        for (CareEstimateJpaEntity findEstimate : findEstimates) {
            estimate.add(findEstimate.toModel());
        }

        return estimate;
    }

    @Override
    public List<CareEstimate> findCareEstimatesByVetIdAndProposal(Long vetId) {
        List<CareEstimate> estimate = new ArrayList<>();
        List<CareEstimateJpaEntity> findEstimates = careEstimateJpaRepository.findCareEstimatesByVetIdAndProposal(vetId, Proposal.DESIGNATION);

        for (CareEstimateJpaEntity findEstimate : findEstimates) {
            estimate.add(findEstimate.toModel());
        }

        return estimate;
    }

    @Override
    public List<CareEstimate> findCareEstimatesByVetId(Long vetId) {
        return careEstimateJpaRepository.findByVetId(vetId).stream().map(CareEstimateJpaEntity::toModel).toList();
    }
}

