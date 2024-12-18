package ddog.persistence.rdb.adapter;

import ddog.domain.estimate.EstimateStatus;
import ddog.domain.estimate.GroomingEstimate;
import ddog.domain.estimate.Proposal;
import ddog.domain.estimate.port.GroomingEstimatePersist;
import ddog.persistence.rdb.jpa.entity.GroomingEstimateJpaEntity;
import ddog.persistence.rdb.jpa.repository.GroomingEstimateJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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
    public Optional<GroomingEstimate> findByEstimateId(Long estimateId) {
        return groomingEstimateJpaRepository.findByEstimateId(estimateId)
                .map(GroomingEstimateJpaEntity::toModel);
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
    public Optional<GroomingEstimate> findByEstimateStatusAndProposalAndPetId(EstimateStatus estimateStatus, Proposal proposal, Long petId) {
        return groomingEstimateJpaRepository.findTopByStatusAndProposalAndPetId(estimateStatus, proposal, petId)
                .map(GroomingEstimateJpaEntity::toModel);
    }

    public Page<GroomingEstimate> findByStatusAndProposalAndAddress(EstimateStatus status, Proposal proposal, String address, Pageable pageable) {
        return groomingEstimateJpaRepository.findByStatusAndProposalAndAddress(status, proposal, address, pageable)
                .map(GroomingEstimateJpaEntity::toModel);
    }

    @Override
    public Page<GroomingEstimate> findByStatusAndProposalAndGroomerId(EstimateStatus status, Proposal proposal, Long groomerId, Pageable pageable) {
        return groomingEstimateJpaRepository.findByStatusAndProposalAndGroomerId(status, proposal, groomerId, pageable)
                .map(GroomingEstimateJpaEntity::toModel);
    }

    @Override
    public List<GroomingEstimate> findGroomingEstimatesByGroomerIdAndEstimateStatus(Long groomerAccountId) {
        List<GroomingEstimate> estimate = new ArrayList<>();
        List<GroomingEstimateJpaEntity> findEstimates = groomingEstimateJpaRepository.findAllByGroomerIdAndStatus(groomerAccountId, EstimateStatus.ON_RESERVATION);

        for (GroomingEstimateJpaEntity findEstimate : findEstimates) {
            estimate.add(findEstimate.toModel());
        }

        return estimate;
    }

    @Override
    public List<GroomingEstimate> findGroomingEstimatesByGroomerIdAndProposal(Long groomerAccountId) {
        List<GroomingEstimate> estimate = new ArrayList<>();
        List<GroomingEstimateJpaEntity> findEstimates = groomingEstimateJpaRepository.findAllByGroomerIdAndProposal(groomerAccountId, Proposal.DESIGNATION);

        for (GroomingEstimateJpaEntity findEstimate : findEstimates) {
            estimate.add(findEstimate.toModel());
        }
        return estimate;
    }

    @Override
    public List<GroomingEstimate> findGroomingEstimatesByGroomerId(Long groomerAccountId) {
        return groomingEstimateJpaRepository.findAllByGroomerId(groomerAccountId).stream().map(GroomingEstimateJpaEntity::toModel).toList();
    }

    @Override
    public List<GroomingEstimate> findTodayGroomingSchedule(Long groomerAccountId, LocalDateTime startOfDay, LocalDateTime endOfDay, EstimateStatus estimateStatus) {
        return groomingEstimateJpaRepository.findTodayScheduleByGroomerId(startOfDay, endOfDay, groomerAccountId, estimateStatus)
                .stream().map(GroomingEstimateJpaEntity::toModel).toList();
    }

    @Override
    public List<GroomingEstimate> findMyEstimatesByUserId(Long userId) {
        return groomingEstimateJpaRepository.findGroomingEstimateJpaEntitiesByUserId(userId).stream().map(GroomingEstimateJpaEntity::toModel).toList();
    }

    @Override
    public GroomingEstimate findEstimateByPetIdAndGroomerAccountId(Long petId, Long groomerAccountId) {
        return groomingEstimateJpaRepository.findGroomingEstimateJpaEntityByPetIdAndGroomerId(petId, groomerAccountId).orElseThrow().toModel();
    }

    @Override
    public Integer countEstimateByGroomerIdDistinctParentId(Long groomerId) {
        return groomingEstimateJpaRepository.countDistinctParentIdsByGroomerAccountId(groomerId);
    }

    @Override
    public List<GroomingEstimate> findByGroomingEstimateStatusBy(Long userId, EstimateStatus estimateStatus) {
        return groomingEstimateJpaRepository.findGroomingEstimateJpaEntitiesByUserIdAndStatus(userId, estimateStatus).stream().map(GroomingEstimateJpaEntity::toModel).toList();
    }
}
