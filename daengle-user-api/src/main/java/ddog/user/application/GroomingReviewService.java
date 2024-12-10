package ddog.user.application;

import ddog.domain.groomer.Groomer;
import ddog.domain.payment.Reservation;
import ddog.domain.review.GroomingReview;
import ddog.user.presentation.review.dto.request.ModifyGroomingReviewInfo;
import ddog.user.presentation.review.dto.request.PostGroomingReviewInfo;
import ddog.domain.user.User;
import ddog.domain.groomer.port.GroomerPersist;
import ddog.domain.review.port.GroomingReviewPersist;
import ddog.domain.payment.port.ReservationPersist;
import ddog.user.application.exception.account.UserException;
import ddog.user.application.exception.account.UserExceptionType;
import ddog.user.application.exception.estimate.ReservationException;
import ddog.user.application.exception.estimate.ReservationExceptionType;
import ddog.user.application.exception.ReviewException;
import ddog.user.application.exception.ReviewExceptionType;
import ddog.domain.user.port.UserPersist;
import ddog.user.application.mapper.GroomingReviewMapper;
import ddog.user.presentation.review.dto.response.GroomingReviewDetailResp;
import ddog.user.presentation.review.dto.response.GroomingReviewListResp;
import ddog.user.presentation.review.dto.response.GroomingReviewSummaryResp;
import ddog.user.presentation.review.dto.response.ReviewResp;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroomingReviewService {

    private final GroomingReviewPersist groomingReviewPersist;
    private final ReservationPersist reservationPersist;
    private final GroomerPersist groomerPersist;
    private final UserPersist userPersist;

    public ReviewResp postReview(PostGroomingReviewInfo postGroomingReviewInfo) {
        Reservation reservation = reservationPersist.findByReservationId(postGroomingReviewInfo.getReservationId()).orElseThrow(()
                -> new ReservationException(ReservationExceptionType.RESERVATION_NOT_FOUND));

        validatePostGroomingReviewInfoDataFormat(postGroomingReviewInfo);

        GroomingReview groomingReviewToSave = GroomingReviewMapper.createBy(reservation, postGroomingReviewInfo);
        GroomingReview SavedGroomingReview = groomingReviewPersist.save(groomingReviewToSave);

        //TODO 댕글미터 계산해서 영속

        return ReviewResp.builder()
                .reviewId(SavedGroomingReview.getGroomingReviewId())
                .reviewerId(SavedGroomingReview.getReviewerId())
                .revieweeId(SavedGroomingReview.getGroomerId())
                .build();
    }

    public ReviewResp updateReview(Long reviewId, ModifyGroomingReviewInfo modifyGroomingReviewInfo) {
        GroomingReview savedGroomingReview = groomingReviewPersist.findByReviewId(reviewId)
                .orElseThrow(() -> new ReviewException(ReviewExceptionType.REVIEW_NOT_FOUND));

        validateModifyGroomingReviewInfoDataFormat(modifyGroomingReviewInfo);

        GroomingReview modifiedReview = GroomingReviewMapper.modifyBy(savedGroomingReview, modifyGroomingReviewInfo);
        GroomingReview updatedGroomingReview = groomingReviewPersist.save(modifiedReview);

        //TODO 댕글미터 재계산해서 영속

        return ReviewResp.builder()
                .reviewId(updatedGroomingReview.getGroomingReviewId())
                .reviewerId(updatedGroomingReview.getReviewerId())
                .revieweeId(updatedGroomingReview.getGroomerId())
                .build();
    }

    public GroomingReviewDetailResp findReview(Long reviewId) {
        GroomingReview savedGroomingReview = groomingReviewPersist.findByReviewId(reviewId)
                .orElseThrow(() -> new ReviewException(ReviewExceptionType.REVIEW_NOT_FOUND));

        return GroomingReviewDetailResp.builder()
                .groomingReviewId(savedGroomingReview.getGroomingReviewId())
                .groomerId(savedGroomingReview.getGroomerId())
                .groomingKeywordReviewList(savedGroomingReview.getGroomingKeywordReviewList())
                .revieweeName(savedGroomingReview.getRevieweeName())
                .shopName(savedGroomingReview.getShopName())
                .starRating(savedGroomingReview.getStarRating())
                .content(savedGroomingReview.getContent())
                .imageUrlList(savedGroomingReview.getImageUrlList())
                .build();
    }

    public ReviewResp deleteReview(Long reviewId) {
        GroomingReview savedGroomingReview = groomingReviewPersist.findByReviewId(reviewId)
                .orElseThrow(() -> new ReviewException(ReviewExceptionType.REVIEW_NOT_FOUND));

        groomingReviewPersist.delete(savedGroomingReview);

        //TODO 댕글미터 재계산해서 영속

        return ReviewResp.builder()
                .reviewId(savedGroomingReview.getGroomingReviewId())
                .reviewerId(savedGroomingReview.getReviewerId())
                .revieweeId(savedGroomingReview.getGroomerId())
                .build();
    }

    public GroomingReviewListResp findMyReviewList(Long accountId, int page, int size) {
        User savedUser = userPersist.findByAccountId(accountId)
                .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));

        Pageable pageable = PageRequest.of(page, size);
        Page<GroomingReview> groomingReviews = groomingReviewPersist.findByReviewerId(savedUser.getAccountId(), pageable);

        return mappingToGroomingReviewListResp(groomingReviews);
    }

    public GroomingReviewListResp findGroomerReviewList(Long groomerId, int page, int size) {
        Groomer savedGroomer = groomerPersist.findByAccountId(groomerId)
                .orElseThrow(() -> new ReviewException(ReviewExceptionType.REVIEWWEE_NOT_FOUNT));

        Pageable pageable = PageRequest.of(page, size);
        Page<GroomingReview> groomingReviews = groomingReviewPersist.findByGroomerId(savedGroomer.getGroomerId(), pageable);

        return mappingToGroomingReviewListResp(groomingReviews);
    }

    private void validatePostGroomingReviewInfoDataFormat(PostGroomingReviewInfo postGroomingReviewInfo) {
        GroomingReview.validateStarRating(postGroomingReviewInfo.getStarRating());
        GroomingReview.validateGroomingKeywordReviewList(postGroomingReviewInfo.getGroomingKeywordReviewList());
        GroomingReview.validateContent(postGroomingReviewInfo.getContent());
        GroomingReview.validateImageUrlList(postGroomingReviewInfo.getImageUrlList());
    }

    private void validateModifyGroomingReviewInfoDataFormat(ModifyGroomingReviewInfo modifyGroomingReviewInfo) {
        GroomingReview.validateStarRating(modifyGroomingReviewInfo.getStarRating());
        GroomingReview.validateGroomingKeywordReviewList(modifyGroomingReviewInfo.getGroomingKeywordReviewList());
        GroomingReview.validateContent(modifyGroomingReviewInfo.getContent());
        GroomingReview.validateImageUrlList(modifyGroomingReviewInfo.getImageUrlList());
    }

    private GroomingReviewListResp mappingToGroomingReviewListResp(Page<GroomingReview> groomingReviews) {
        List<GroomingReviewSummaryResp> groomingReviewList = groomingReviews.stream().map(groomingReview ->
                GroomingReviewSummaryResp.builder()
                        .groomingReviewId(groomingReview.getGroomingReviewId())
                        .groomerId(groomingReview.getGroomerId())
                        .groomingKeywordReviewList(groomingReview.getGroomingKeywordReviewList())
                        .revieweeName(groomingReview.getRevieweeName())
                        .starRating(groomingReview.getStarRating())
                        .content(groomingReview.getContent())
                        .imageUrlList(groomingReview.getImageUrlList())
                        .build()
        ).toList();

        return GroomingReviewListResp.builder()
                .reviewCount(groomingReviews.getTotalElements())
                .reviewList(groomingReviewList)
                .build();
    }
}
