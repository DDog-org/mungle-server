package ddog.persistence.mysql.adapter;

import ddog.domain.review.CareReview;
import ddog.persistence.mysql.jpa.entity.CareReviewJpaEntity;
import ddog.persistence.mysql.jpa.repository.CareReviewJpaRepository;
import ddog.persistence.mysql.port.CareReviewPersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<CareReview> findByReviewerId(Long reviewerId) {
        return careReviewJpaRepository.findByReviewerId(reviewerId).stream()
                .map(CareReviewJpaEntity::toModel)
                .collect(Collectors.toList());
    }
}

