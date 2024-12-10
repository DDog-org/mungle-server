package ddog.persistence.mysql.adapter;

import ddog.domain.review.CareReview;
import ddog.persistence.mysql.jpa.entity.CareReviewJpaEntity;
import ddog.persistence.mysql.jpa.repository.CareReviewJpaRepository;
import ddog.domain.review.port.CareReviewPersist;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CareReviewRepository implements CareReviewPersist {

    private final CareReviewJpaRepository careReviewJpaRepository;

    @Override
    public Optional<CareReview> findBy(Long careReviewId) {
        return careReviewJpaRepository.findById(careReviewId).map(CareReviewJpaEntity::toModel);
    }

    @Override
    public CareReview save(CareReview careReview) {
        CareReviewJpaEntity careReviewJpaEntity = careReviewJpaRepository.save(CareReviewJpaEntity.from(careReview));
        return careReviewJpaEntity.toModel();
    }

    @Override
    public void delete(CareReview careReview) {
        careReviewJpaRepository.delete(CareReviewJpaEntity.from(careReview));
    }

    @Override
    public Page<CareReview> findByReviewerId(Long reviewerId, Pageable pageable) {
        return careReviewJpaRepository.findByReviewerId(reviewerId, pageable).map(CareReviewJpaEntity::toModel);
    }

    @Override
    public Page<CareReview> findByVetId(Long vetId, Pageable pageable) {
        return careReviewJpaRepository.findByVetId(vetId, pageable).map(CareReviewJpaEntity::toModel);
    }
}

