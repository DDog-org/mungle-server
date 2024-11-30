package ddog.persistence.adapter;

import ddog.domain.estimate.CareEstimate;
import ddog.persistence.jpa.entity.CareEstimateJpaEntity;
import ddog.persistence.jpa.repository.CareEstimateJpaRepository;
import ddog.persistence.port.CareEstimatePersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CareEstimateRepository implements CareEstimatePersist {

    private final CareEstimateJpaRepository careEstimateJpaRepository;

    @Override
    public void save(CareEstimate careEstimate) {
        careEstimateJpaRepository.save(CareEstimateJpaEntity.from(careEstimate));
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
    public CareEstimate getByCareEstimateId(Long careEstimateId) {
        return careEstimateJpaRepository.getCareEstimateJpaEntityByCareEstimateId(careEstimateId).toModel();
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
