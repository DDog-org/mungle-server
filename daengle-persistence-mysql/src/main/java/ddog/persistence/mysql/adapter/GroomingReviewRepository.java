package ddog.persistence.mysql.adapter;

import ddog.domain.review.GroomingReview;
import ddog.persistence.mysql.jpa.entity.GroomingReviewJpaEntity;
import ddog.persistence.mysql.jpa.repository.GroomingReviewJpaRepository;
import ddog.domain.review.port.GroomingReviewPersist;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GroomingReviewRepository implements GroomingReviewPersist {

    private final GroomingReviewJpaRepository groomingReviewJpaRepository;

    @Override
    public Optional<GroomingReview> findBy(Long groomingId) {
        return groomingReviewJpaRepository.findById(groomingId).map(GroomingReviewJpaEntity::toModel);
    }

    @Override
    public GroomingReview save(GroomingReview groomingReview) {
        GroomingReviewJpaEntity groomingEstimateJpaEntity = groomingReviewJpaRepository.save(GroomingReviewJpaEntity.from(groomingReview));
        return groomingEstimateJpaEntity.toModel();
    }

    @Override
    public void delete(GroomingReview groomingReview) {
        groomingReviewJpaRepository.delete(GroomingReviewJpaEntity.from(groomingReview));
    }

    @Override
    public Page<GroomingReview> findByReviewerId(Long reviewerId, Pageable pageable) {
        return groomingReviewJpaRepository.findByReviewerId(reviewerId, pageable).map(GroomingReviewJpaEntity::toModel);
    }

    @Override
    public Page<GroomingReview> findByGroomerId(Long groomerId, Pageable pageable) {
        return groomingReviewJpaRepository.findByGroomerId(groomerId, pageable).map(GroomingReviewJpaEntity::toModel);
    }
}
