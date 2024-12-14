package ddog.user.application;

import ddog.domain.filtering.BanWordValidator;
import ddog.domain.payment.Reservation;
import ddog.domain.review.CareReview;
import ddog.user.application.mapper.CareReviewMapper;
import ddog.user.presentation.review.dto.response.CareReviewListResp;
import ddog.user.presentation.review.dto.request.UpdateCareReviewInfo;
import ddog.user.presentation.review.dto.request.PostCareReviewInfo;
import ddog.domain.user.User;
import ddog.domain.vet.Vet;
import ddog.domain.review.port.CareReviewPersist;
import ddog.domain.payment.port.ReservationPersist;
import ddog.domain.user.port.UserPersist;
import ddog.domain.vet.port.VetPersist;
import ddog.user.application.exception.*;
import ddog.user.application.exception.account.UserException;
import ddog.user.application.exception.account.UserExceptionType;
import ddog.user.application.exception.estimate.ReservationException;
import ddog.user.application.exception.estimate.ReservationExceptionType;
import ddog.user.presentation.review.dto.response.CareReviewDetailResp;
import ddog.user.presentation.review.dto.response.ReviewResp;
import ddog.user.presentation.review.dto.response.CareReviewSummaryResp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CareReviewService {

    private final CareReviewPersist careReviewPersist;
    private final ReservationPersist reservationPersist;
    private final VetPersist vetPersist;
    private final UserPersist userPersist;

    private final BanWordValidator banWordValidator;

    @Transactional(readOnly = true)
    public CareReviewDetailResp findReview(Long reviewId) {
        CareReview savedCareReview = careReviewPersist.findByReviewId(reviewId)
                .orElseThrow(() -> new ReviewException(ReviewExceptionType.REVIEW_NOT_FOUND));

        Reservation savedReservation = reservationPersist.findByReservationId(savedCareReview.getReservationId())
                .orElseThrow(() -> new ReservationException(ReservationExceptionType.RESERVATION_NOT_FOUND));

        return CareReviewDetailResp.builder()
                .careReviewId(savedCareReview.getCareReviewId())
                .vetId(savedCareReview.getVetId())
                .careKeywordList(savedCareReview.getCareKeywordList())
                .revieweeName(savedCareReview.getRevieweeName())
                .shopName(savedCareReview.getShopName())
                .starRating(savedCareReview.getStarRating())
                .schedule(savedReservation.getSchedule())
                .content(savedCareReview.getContent())
                .imageUrlList(savedCareReview.getImageUrlList())
                .build();
    }

    @Transactional
    public ReviewResp postReview(PostCareReviewInfo postCareReviewInfo) {
        Reservation reservation = reservationPersist.findByReservationId(postCareReviewInfo.getReservationId()).orElseThrow(()
                -> new ReservationException(ReservationExceptionType.RESERVATION_NOT_FOUND));

        validatePostCareReviewInfoDataFormat(postCareReviewInfo);

        String includedBanWord = banWordValidator.getBanWords(postCareReviewInfo.getContent());
        if(includedBanWord != null) throw new ReviewException(ReviewExceptionType.REVIEW_CONTENT_CONTAIN_BAN_WORD, includedBanWord);

        CareReview careReviewToSave = CareReviewMapper.createBy(reservation, postCareReviewInfo);
        CareReview savedCareReview = careReviewPersist.save(careReviewToSave);

        //TODO 댕글미터 계산해서 영속

        return ReviewResp.builder()
                .reviewId(savedCareReview.getCareReviewId())
                .reviewerId(savedCareReview.getReviewerId())
                .revieweeId(savedCareReview.getVetId())
                .banWord(null)
                .build();
    }

    @Transactional
    public ReviewResp updateReview(Long reviewId, UpdateCareReviewInfo updateCareReviewInfo) {
        CareReview savedCareReview = careReviewPersist.findByReviewId(reviewId)
                .orElseThrow(() -> new ReviewException(ReviewExceptionType.REVIEW_NOT_FOUND));

        validateModifyCareReviewInfoDataFormat(updateCareReviewInfo);

        String includedBanWord = banWordValidator.getBanWords(updateCareReviewInfo.getContent());
        if(includedBanWord != null) throw new ReviewException(ReviewExceptionType.REVIEW_CONTENT_CONTAIN_BAN_WORD, includedBanWord);

        CareReview modifiedReview = CareReviewMapper.modifyBy(savedCareReview, updateCareReviewInfo);
        CareReview updatedCareReview = careReviewPersist.save(modifiedReview);

        //TODO 댕글미터 재계산해서 영속

        return ReviewResp.builder()
                .reviewId(updatedCareReview.getCareReviewId())
                .reviewerId(updatedCareReview.getReviewerId())
                .revieweeId(updatedCareReview.getVetId())
                .banWord(null)

                .build();
    }

    @Transactional
    public ReviewResp deleteReview(Long reviewId) {
        CareReview savedCareReview = careReviewPersist.findByReviewId(reviewId)
                .orElseThrow(() -> new ReviewException(ReviewExceptionType.REVIEW_NOT_FOUND));

        careReviewPersist.delete(savedCareReview);

        return ReviewResp.builder()
                .reviewId(savedCareReview.getCareReviewId())
                .reviewerId(savedCareReview.getReviewerId())
                .revieweeId(savedCareReview.getVetId())
                .build();
    }

    @Transactional(readOnly = true)
    public CareReviewListResp findMyReviewList(Long accountId, int page, int size) {
        User savedUser = userPersist.findByAccountId(accountId)
                .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));

        Pageable pageable = PageRequest.of(page, size);
        Page<CareReview> careReviews = careReviewPersist.findByReviewerId(savedUser.getAccountId(), pageable);

        return mappingToCareReviewListResp(careReviews);
    }

    public CareReviewListResp findVetReviewList(Long vetId, int page, int size) {
        Vet savedVet = vetPersist.findByVetId(vetId)
                .orElseThrow(() -> new ReviewException(ReviewExceptionType.REVIEWWEE_NOT_FOUNT));

        Pageable pageable = PageRequest.of(page, size);
        Page<CareReview> careReviews = careReviewPersist.findByVetId(savedVet.getVetId(), pageable);

        return mappingToCareReviewListResp(careReviews);
    }

    private void validatePostCareReviewInfoDataFormat(PostCareReviewInfo postCareReviewInfo) {
        CareReview.validateStarRating(postCareReviewInfo.getStarRating());
        CareReview.validateCareKeywordReviewList(postCareReviewInfo.getCareKeywordList());
        CareReview.validateContent(postCareReviewInfo.getContent());
        CareReview.validateImageUrlList(postCareReviewInfo.getImageUrlList());
    }

    private void validateModifyCareReviewInfoDataFormat(UpdateCareReviewInfo updateCareReviewInfo) {
        CareReview.validateStarRating(updateCareReviewInfo.getStarRating());
        CareReview.validateCareKeywordReviewList(updateCareReviewInfo.getCareKeywordList());
        CareReview.validateContent(updateCareReviewInfo.getContent());
        CareReview.validateImageUrlList(updateCareReviewInfo.getImageUrlList());
    }

    private CareReviewListResp mappingToCareReviewListResp(Page<CareReview> careReviews) {
        List<CareReviewSummaryResp> careReviewList = careReviews.stream().map(careReview -> {

            User reviewer = userPersist.findByAccountId(careReview.getReviewerId())
                    .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));

            reservationPersist.findByReservationId(careReview.getReservationId())
                    .orElseThrow(() -> new ReservationException(ReservationExceptionType.RESERVATION_NOT_FOUND));

            return CareReviewSummaryResp.builder()
                    .careReviewId(careReview.getCareReviewId())
                    .reviewerName(reviewer.getUsername())
                    .reviewerImageUrl(reviewer.getImageUrl())
                    .vetId(careReview.getVetId())
                    .careKeywordList(careReview.getCareKeywordList())
                    .revieweeName(careReview.getRevieweeName())
                    .createdAt(careReview.getCreatedAt())
                    .starRating(careReview.getStarRating())
                    .content(careReview.getContent())
                    .imageUrlList(careReview.getImageUrlList())
                    .build();
        }).toList(); // careReviewList

        return CareReviewListResp.builder()
                .reviewCount(careReviews.getTotalElements())
                .reviewList(careReviewList)
                .build();
    }
}
