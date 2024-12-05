package ddog.persistence.mysql.adapter;

import ddog.domain.estimate.CareEstimate;
import ddog.persistence.mysql.jpa.entity.CareEstimateJpaEntity;
import ddog.persistence.mysql.jpa.repository.CareEstimateJpaRepository;
import ddog.persistence.mysql.port.CareEstimatePersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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
    public List<CareEstimate> findGeneralCareEstimates(String address) {
        List<CareEstimate> careEstimates = new ArrayList<>();
        for (CareEstimateJpaEntity careEstimateJpaEntity : careEstimateJpaRepository.findGeneralCareEstimatesByAddress(address)) {
            careEstimates.add(careEstimateJpaEntity.toModel());
        }
        return careEstimates;
    }

    @Override
    public List<CareEstimate> findDesignationCareEstimates(Long vetId) {
        List<CareEstimate> careEstimates = new ArrayList<>();
        for (CareEstimateJpaEntity careEstimateJpaEntity : careEstimateJpaRepository.findDesignationCareEstimatesByVetId(vetId)) {
            careEstimates.add(careEstimateJpaEntity.toModel());
        }
        return careEstimates;
    }

    @Override
    public CareEstimate getByEstimateId(Long estimateId) {
        return careEstimateJpaRepository.getCareEstimateJpaEntityByEstimateId(estimateId).toModel();
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
