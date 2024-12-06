package ddog.persistence.mysql.adapter;

import ddog.domain.review.CareReview;
import ddog.persistence.mysql.jpa.entity.CareReviewJpaEntity;
import ddog.persistence.mysql.jpa.repository.CareReviewJpaRepository;
import ddog.persistence.mysql.port.CareReviewPersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CareReviewRepository implements CareReviewPersist {

    private final CareReviewJpaRepository careReviewJpaRepository;

    @Override
    public CareReview save(CareReview careReview) {
        CareReviewJpaEntity careReviewJpaEntity = careReviewJpaRepository.save(CareReviewJpaEntity.from(careReview));
        return careReviewJpaEntity.toModel();
    }
}

