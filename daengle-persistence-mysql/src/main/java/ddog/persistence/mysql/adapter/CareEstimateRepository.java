package ddog.persistence.mysql.adapter;

import ddog.domain.estimate.CareEstimate;
import ddog.domain.estimate.EstimateStatus;
import ddog.persistence.mysql.jpa.entity.CareEstimateJpaEntity;
import ddog.persistence.mysql.jpa.repository.CareEstimateJpaRepository;
import ddog.domain.estimate.port.CareEstimatePersist;
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
    public List<CareEstimate> findCareEstimatesByAddress(String address) {
        List<CareEstimate> careEstimates = new ArrayList<>();

        for (CareEstimateJpaEntity careEstimateJpaEntity : careEstimateJpaRepository.findCareEstimatesByAddress(address)) {
            careEstimates.add(careEstimateJpaEntity.toModel());
        }

        return careEstimates;
    }

    @Override
    public List<CareEstimate> findCareEstimatesByVetId(Long vetId) {
        List<CareEstimate> careEstimates = new ArrayList<>();

        for (CareEstimateJpaEntity careEstimateJpaEntity : careEstimateJpaRepository.findCareEstimatesByVetId(vetId)) {
            careEstimates.add(careEstimateJpaEntity.toModel());
        }

        return careEstimates;
    }

    @Override
    public void updateStatusWithParentId(EstimateStatus estimateStatus, Long parentId) {
        careEstimateJpaRepository.updateParentEstimateByParentId(estimateStatus, parentId);
        careEstimateJpaRepository.updateStatusByParentId(estimateStatus, parentId);
    }

    @Override
    public Page<CareEstimate> findByPetIdAndPageable(Long petId, Pageable pageable) {
        return careEstimateJpaRepository.findByPetId(petId, pageable)
                .map(CareEstimateJpaEntity::toModel);
    }

    @Override
    public Optional<CareEstimate> findByEstimateId(Long estimateId) {
        return careEstimateJpaRepository.findByEstimateId(estimateId)
                .map(CareEstimateJpaEntity::toModel);
    }

    @Override
    public List<CareEstimate> findCareEstimatesByPetId(Long petId) {
        List<CareEstimate> careEstimates = new ArrayList<>();

        for (CareEstimateJpaEntity careEstimateJpaEntity : careEstimateJpaRepository.findCareEstimatesByPetId(petId)) {
            careEstimates.add(careEstimateJpaEntity.toModel());
        }

        return careEstimates;
    }
}
