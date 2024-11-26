package ddog.daengleserver.infrastructure;

import ddog.daengleserver.application.repository.GroomingEstimateRepository;
import ddog.daengleserver.domain.estimate.GroomingEstimate;
import ddog.daengleserver.infrastructure.po.GroomingEstimateJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GroomingEstimateRepositoryImpl implements GroomingEstimateRepository {

    private final GroomingEstimateJpaRepository groomingEstimateJpaRepository;

    @Override
    public void save(GroomingEstimate groomingEstimate) {
        groomingEstimateJpaRepository.save(GroomingEstimateJpaEntity.from(groomingEstimate));
    }

    @Override
    public List<GroomingEstimate> findGeneralGroomingEstimates(String address) {
        List<GroomingEstimate> groomingEstimates = new ArrayList<>();
        for (GroomingEstimateJpaEntity groomingEstimateJpaEntity : groomingEstimateJpaRepository.findGeneralGroomingEstimatesByAddress(address)) {
            groomingEstimates.add(groomingEstimateJpaEntity.toModel());
        }
        return groomingEstimates;
    }

    @Override
    public List<GroomingEstimate> findDesignationGroomingEstimates(Long groomerId) {
        List<GroomingEstimate> groomingEstimates = new ArrayList<>();
        for (GroomingEstimateJpaEntity groomingEstimateJpaEntity : groomingEstimateJpaRepository.findDesignationGroomingEstimatesByGroomerId(groomerId)) {
            groomingEstimates.add(groomingEstimateJpaEntity.toModel());
        }
        return groomingEstimates;
    }

    @Override
    public GroomingEstimate getByGroomingEstimateId(Long groomingEstimateId) {
        return groomingEstimateJpaRepository.getGroomingEstimateJpaEntityByGroomingEstimateId(groomingEstimateId)
                .toModel();
    }

    @Override
    public List<GroomingEstimate> findGroomingEstimatesByPetId(Long petId) {
        List<GroomingEstimate> groomingEstimates = new ArrayList<>();
        for (GroomingEstimateJpaEntity groomingEstimateJpaEntity : groomingEstimateJpaRepository.findGroomingEstimatesByPetId(petId)) {
            groomingEstimates.add(groomingEstimateJpaEntity.toModel());
        }
        return groomingEstimates;
    }
}
