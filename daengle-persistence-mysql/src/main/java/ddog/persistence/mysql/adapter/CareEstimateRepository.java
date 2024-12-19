package ddog.persistence.mysql.adapter;

import ddog.domain.estimate.CareEstimate;
import ddog.domain.estimate.EstimateStatus;
import ddog.domain.estimate.Proposal;
import ddog.domain.estimate.dto.PetInfos;
import ddog.domain.estimate.port.CareEstimatePersist;
import ddog.persistence.mysql.jpa.entity.CareEstimateJpaEntity;
import ddog.persistence.mysql.jpa.repository.CareEstimateJpaRepository;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<PetInfos.Content> findByStatusAndProposalAndUserId(EstimateStatus status, Proposal proposal, Long userId) {
        List<Tuple> results = careEstimateJpaRepository.findByStatusAndProposalAndUserId(status, proposal, userId);

        return results.stream()
                .map(tuple -> PetInfos.Content.builder()
                        .estimateId(tuple.get("estimateId", Long.class))
                        .petId(tuple.get("petId", Long.class))
                        .imageUrl(tuple.get("imageUrl", String.class))
                        .name(tuple.get("name", String.class))
                        .build())
                .collect(Collectors.toList());
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

    public List<CareEstimate> findCareEstimatesByVetIdAndEstimateStatus(Long vetAccountId) {
        List<CareEstimate> estimate = new ArrayList<>();
        List<CareEstimateJpaEntity> findEstimates = careEstimateJpaRepository.findCareEstimatesByVetIdAndStatus(vetAccountId, EstimateStatus.ON_RESERVATION);

        for (CareEstimateJpaEntity findEstimate : findEstimates) {
            estimate.add(findEstimate.toModel());
        }

        return estimate;
    }

    @Override
    public List<CareEstimate> findCareEstimatesByVetIdAndProposal(Long vetAccountId) {
        List<CareEstimate> estimate = new ArrayList<>();
        List<CareEstimateJpaEntity> findEstimates = careEstimateJpaRepository.findCareEstimatesByVetIdAndProposal(vetAccountId, Proposal.DESIGNATION);

        for (CareEstimateJpaEntity findEstimate : findEstimates) {
            estimate.add(findEstimate.toModel());
        }

        return estimate;
    }

    @Override
    public List<CareEstimate> findCareEstimatesByVetId(Long vetAccountId) {
        return careEstimateJpaRepository.findByVetId(vetAccountId).stream().map(CareEstimateJpaEntity::toModel).toList();
    }

    public List<CareEstimate> findTodayCareSchedule(Long vetAccountId, LocalDateTime startOfDay, LocalDateTime endOfDay, EstimateStatus estimateStatus) {
        return careEstimateJpaRepository.finTodayScheduleByVetId(startOfDay, endOfDay, vetAccountId, estimateStatus)
                .stream().map(CareEstimateJpaEntity::toModel).toList();
    }

    @Override
    public List<CareEstimate> findMyEstimatesByUserId(Long userId) {
        return careEstimateJpaRepository.findCareEstimateJpaEntitiesByUserId(userId).stream().map(CareEstimateJpaEntity::toModel).toList();
    }

    @Override
    public Optional<CareEstimate> findEstimateByUserIdAndPetId(Long userId, Long petId) {
        return careEstimateJpaRepository.findCareEstimateJpaEntityByUserIdAndPetId(userId, petId).map(CareEstimateJpaEntity::toModel);
    }

    @Override
    public Integer countEstimateByVetIdDistinctParentId(Long vetId) {
        return careEstimateJpaRepository.countDistinctParentIdsByVetAccountId(vetId);
    }

    @Override
    public List<CareEstimate> findByCareEstimateStatusBy(Long userId, EstimateStatus estimateStatus) {
        return careEstimateJpaRepository.findCareEstimateJpaEntitiesByUserIdAndStatus(userId, estimateStatus).stream().map(CareEstimateJpaEntity::toModel).toList();
    }
}

