package ddog.daengleserver.infrastructure;

import ddog.daengleserver.application.repository.CareEstimateRepository;
import ddog.daengleserver.domain.estimate.CareEstimate;
import ddog.daengleserver.infrastructure.po.CareEstimateJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CareEstimateRepositoryImpl implements CareEstimateRepository {

    private final CareEstimateJpaRepository careEstimateJpaRepository;

    @Override
    public void save(CareEstimate careEstimate) {
        careEstimateJpaRepository.save(CareEstimateJpaEntity.from(careEstimate));
    }

    @Override
    public List<CareEstimate> findCareEstimatesByAddress(String address) {

        List<CareEstimate> careEstimates = new ArrayList<>();
        for (CareEstimateJpaEntity careEstimateJpaEntity : careEstimateJpaRepository.findCareEstimatesJpaEntitiesByAddress(address)) {
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
        for (CareEstimateJpaEntity careEstimateJpaEntity : careEstimateJpaRepository.findCareEstimateJpaEntitiesByPetId(petId)) {
            careEstimates.add(careEstimateJpaEntity.toModel());
        }
        return careEstimates;
    }
}
