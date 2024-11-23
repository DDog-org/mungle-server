package ddog.daengleserver.infrastructure;

import ddog.daengleserver.application.repository.GroomingEstimateRepository;
import ddog.daengleserver.domain.estimate.GroomingEstimate;
import ddog.daengleserver.infrastructure.po.GroomingEstimateJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GroomingEstimateEstimateRepositoryImpl implements GroomingEstimateRepository {

    private final GroomingEstimateJpaRepository groomingEstimateJpaRepository;

    @Override
    public void save(GroomingEstimate groomingEstimate) {
        groomingEstimateJpaRepository.save(GroomingEstimateJpaEntity.from(groomingEstimate));
    }
}
