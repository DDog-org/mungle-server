package ddog.user.application;

import ddog.domain.payment.Reservation;
import ddog.domain.review.CareReview;
import ddog.domain.review.dto.ModifyCareReviewInfo;
import ddog.domain.review.dto.PostCareReviewInfo;
import ddog.domain.user.User;
import ddog.domain.vet.Vet;
import ddog.persistence.mysql.port.CareReviewPersist;
import ddog.persistence.mysql.port.ReservationPersist;
import ddog.persistence.mysql.port.UserPersist;
import ddog.persistence.mysql.port.VetPersist;
import ddog.user.application.exception.*;
import ddog.user.application.exception.estimate.ReservationException;
import ddog.user.application.exception.estimate.ReservationExceptionType;
import ddog.user.application.exception.ReviewException;
import ddog.user.application.exception.ReviewExceptionType;
import ddog.user.presentation.review.dto.ReviewResp;
import ddog.user.presentation.review.dto.CareReviewSummaryResp;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CareReviewService {

    private final CareReviewPersist careReviewPersist;
    private final ReservationPersist reservationPersist;
    private final VetPersist vetPersist;
    private final UserPersist userPersist;

    public ReviewResp postReview(PostCareReviewInfo postCareReviewInfo) {
        Reservation reservation = reservationPersist.findBy(postCareReviewInfo.getReservationId()).orElseThrow(()
                -> new ReservationException(ReservationExceptionType.RESERVATION_NOT_FOUND));

        CareReview careReviewToSave = CareReview.createBy(reservation, postCareReviewInfo);
        CareReview savedCareReview = careReviewPersist.save(careReviewToSave);

        //TODO 댕글미터 계산해서 영속

        return ReviewResp.builder()
                .reviewId(savedCareReview.getCareReviewId())
                .reviewerId(savedCareReview.getReviewerId())
                .revieweeId(savedCareReview.getVetId())
                .build();
    }

    public ReviewResp modifyReview(Long reviewId, ModifyCareReviewInfo modifyCareReviewInfo) {
        CareReview savedCareReview = careReviewPersist.findBy(reviewId)
                .orElseThrow(() -> new ReviewException(ReviewExceptionType.REVIEW_NOT_FOUND));

        CareReview modifiedReview = CareReview.modifyBy(savedCareReview, modifyCareReviewInfo);
        CareReview updatedCareReview = careReviewPersist.save(modifiedReview);

        //TODO 댕글미터 재계산해서 영속

        return ReviewResp.builder()
                .reviewId(updatedCareReview.getCareReviewId())
                .reviewerId(updatedCareReview.getReviewerId())
                .revieweeId(updatedCareReview.getVetId())
                .build();
    }

    public ReviewResp deleteReview(Long reviewId) {
        CareReview savedCareReview = careReviewPersist.findBy(reviewId)
                .orElseThrow(() -> new ReviewException(ReviewExceptionType.REVIEW_NOT_FOUND));

        careReviewPersist.delete(savedCareReview);

        return ReviewResp.builder()
                .reviewId(savedCareReview.getCareReviewId())
                .reviewerId(savedCareReview.getReviewerId())
                .revieweeId(savedCareReview.getVetId())
                .build();
    }

    public List<CareReviewSummaryResp> findMyReviewList(Long accountId, int page, int size) {
        User savedUser = userPersist.findBy(accountId)
                .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));

        Pageable pageable = PageRequest.of(page, size);

        Page<CareReview> careReviews = careReviewPersist.findByReviewerId(savedUser.getUserId(), pageable);

        return careReviews.stream().map(careReview ->
                CareReviewSummaryResp.builder()
                        .careReviewId(careReview.getCareReviewId())
                        .vetId(careReview.getVetId())
                        .careKeywordReviewList(careReview.getCareKeywordReviewList())
                        .revieweeName(careReview.getRevieweeName())
                        .starRating(careReview.getStarRating())
                        .content(careReview.getContent())
                        .build()
        ).toList();
    }

    public List<CareReviewSummaryResp> findVetReviewList(Long vetId, int page, int size) {
        Vet savedVet = vetPersist.findBy(vetId)
                .orElseThrow(() -> new ReviewException(ReviewExceptionType.REVIEWWEE_NOT_FOUNT));

        Pageable pageable = PageRequest.of(page, size);
        Page<CareReview> careReviews = careReviewPersist.findByVetId(savedVet.getVetId(), pageable);

        return careReviews.stream().map(careReview ->
                CareReviewSummaryResp.builder()
                        .careReviewId(careReview.getCareReviewId())
                        .vetId(careReview.getVetId())
                        .careKeywordReviewList(careReview.getCareKeywordReviewList())
                        .revieweeName(careReview.getRevieweeName())
                        .starRating(careReview.getStarRating())
                        .content(careReview.getContent())
                        .build()
        ).toList();
    }
}
