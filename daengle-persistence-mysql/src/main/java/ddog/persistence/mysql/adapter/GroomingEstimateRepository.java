package ddog.persistence.mysql.adapter;

import ddog.domain.estimate.EstimateStatus;
import ddog.domain.estimate.GroomingEstimate;
import ddog.domain.estimate.Proposal;
import ddog.persistence.mysql.jpa.entity.GroomingEstimateJpaEntity;
import ddog.persistence.mysql.jpa.repository.GroomingEstimateJpaRepository;
import ddog.domain.estimate.port.GroomingEstimatePersist;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GroomingEstimateRepository implements GroomingEstimatePersist {

    private final GroomingEstimateJpaRepository groomingEstimateJpaRepository;

    @Override
    public GroomingEstimate save(GroomingEstimate groomingEstimate) {
        return groomingEstimateJpaRepository.save(GroomingEstimateJpaEntity.from(groomingEstimate))
                .toModel();
    }

    @Override
    public List<GroomingEstimate> findGroomingEstimatesByAddress(String address) {
        List<GroomingEstimate> groomingEstimates = new ArrayList<>();

        for (GroomingEstimateJpaEntity groomingEstimateJpaEntity : groomingEstimateJpaRepository.findGroomingEstimatesByAddress(address)) {
            groomingEstimates.add(groomingEstimateJpaEntity.toModel());

        }
        return groomingEstimates;
    }

    @Override
    public List<GroomingEstimate> findGroomingEstimatesByGroomerId(Long groomerId) {
        List<GroomingEstimate> groomingEstimates = new ArrayList<>();
        for (GroomingEstimateJpaEntity groomingEstimateJpaEntity : groomingEstimateJpaRepository.findGroomingEstimatesByGroomerId(groomerId)) {
            groomingEstimates.add(groomingEstimateJpaEntity.toModel());
        }
        return groomingEstimates;
    }

    @Override
    public Optional<GroomingEstimate> findByEstimateId(Long estimateId) {
        return groomingEstimateJpaRepository.findByEstimateId(estimateId)
                .map(GroomingEstimateJpaEntity::toModel);
    }

    @Override
    public List<GroomingEstimate> findByPetIdAndStatusAndPageable(Long petId) {
        List<GroomingEstimate> groomingEstimates = new ArrayList<>();

        for (GroomingEstimateJpaEntity groomingEstimateJpaEntity : groomingEstimateJpaRepository.findGroomingEstimatesByPetId(petId)) {
            groomingEstimates.add(groomingEstimateJpaEntity.toModel());
        }

        return groomingEstimates;
    }

    @Override
    public void updateStatusWithParentId(EstimateStatus estimateStatus, Long parentId) {
        groomingEstimateJpaRepository.updateParentEstimateByParentId(estimateStatus, parentId);
        groomingEstimateJpaRepository.updateStatusByParentId(estimateStatus, parentId);
    }

    public Page<GroomingEstimate> findByPetIdAndStatusAndProposal(Long petId, EstimateStatus status, Proposal proposal, Pageable pageable) {
        return groomingEstimateJpaRepository.findByPetIdAndStatusAndProposal(petId, status, proposal, pageable)
                .map(GroomingEstimateJpaEntity::toModel);
    }

    @Override
    public boolean hasGeneralEstimateByPetId(Long petId) {
        return groomingEstimateJpaRepository.existsNewAndGeneralByPetId(petId);
    }

    @Override
    public boolean hasDesignationEstimateByPetId(Long petId) {
        return groomingEstimateJpaRepository.existsNewAndDesignationByPetId(petId);
    }

    @Override
    public Optional<GroomingEstimate> findByEstimateStatusAndProposalAndPetId(EstimateStatus estimateStatus, Proposal proposal, Long petId) {
        return groomingEstimateJpaRepository.findTopByStatusAndProposalAndPetId(estimateStatus, proposal, petId)
                .map(GroomingEstimateJpaEntity::toModel);
    }
}
