package ddog.persistence.mysql.adapter;

import ddog.domain.review.ReportedReview;
import ddog.domain.review.port.ReportReviewPersist;
import ddog.persistence.mysql.jpa.entity.ReportedReviewJpaEntity;
import ddog.persistence.mysql.jpa.repository.ReportedReviewJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReportedReviewRepository implements ReportReviewPersist {

    private final ReportedReviewJpaRepository reportedReviewJpaRepository;

    @Override
    public ReportedReview save(ReportedReview reportedReview) {
        return reportedReviewJpaRepository.save(ReportedReviewJpaEntity.from(reportedReview)).toModel();
    }

    @Override
    public Page<ReportedReview> findByReporterId(Long vetId, Pageable pageable) {
        return reportedReviewJpaRepository.findByReporterId(vetId, pageable).map(ReportedReviewJpaEntity::toModel);
    }
}
