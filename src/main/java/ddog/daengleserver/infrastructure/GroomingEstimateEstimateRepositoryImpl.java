package ddog.daengleserver.infrastructure;

import ddog.daengleserver.application.repository.GroomingEstimateRepository;
import ddog.daengleserver.domain.estimate.GroomingEstimate;
import ddog.daengleserver.infrastructure.jpa.GroomingEstimateJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GroomingEstimateEstimateRepositoryImpl implements GroomingEstimateRepository {

    private final GroomingEstimateJpaRepository groomingEstimateJpaRepository;

    @Override
    public void save(GroomingEstimate groomingEstimate) {
        groomingEstimateJpaRepository.save(GroomingEstimateJpaEntity.from(groomingEstimate));
    }

    @Override
    public List<GroomingEstimate> findGroomingEstimatesByAddress(String address) {

        List<GroomingEstimateJpaEntity> groomingEstimateJpaEntityList = groomingEstimateJpaRepository.findGroomingEstimatesJpaEntitiesByAddress(address);
        List<GroomingEstimate> groomingEstimates = new ArrayList<>();

        for (GroomingEstimateJpaEntity groomingEstimateJpaEntity : groomingEstimateJpaEntityList) {
            groomingEstimates.add(groomingEstimateJpaEntity.toModel());
        }

        return groomingEstimates;
    }

    @Override
    public GroomingEstimate getByGroomingEstimateId(Long groomingEstimateId) {
        return groomingEstimateJpaRepository.getGroomingEstimateJpaEntityByGroomingEstimateId(groomingEstimateId).toModel();
    }
}
