package ddog.persistence.adapter;

import ddog.domain.estimate.GroomingEstimate;
import ddog.persistence.jpa.entity.GroomingEstimateJpaEntity;
import ddog.persistence.jpa.repository.GroomingEstimateJpaRepository;
import ddog.persistence.port.GroomingEstimatePersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GroomingEstimateRepository implements GroomingEstimatePersist {

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
