package ddog.user.application;

import com.vane.badwordfiltering.BadWordFiltering;
import ddog.domain.groomer.Groomer;
import ddog.domain.payment.Reservation;
import ddog.domain.review.GroomingReview;
import ddog.user.presentation.review.dto.request.UpdateGroomingReviewInfo;
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
public class GroomingReviewService {

    private final GroomingReviewPersist groomingReviewPersist;
    private final ReservationPersist reservationPersist;
    private final GroomerPersist groomerPersist;
    private final UserPersist userPersist;

    private final BadWordFiltering badWordFiltering;

    @Transactional(readOnly = true)
    public GroomingReviewDetailResp findReview(Long reviewId) {
        GroomingReview savedGroomingReview = groomingReviewPersist.findByReviewId(reviewId)
                .orElseThrow(() -> new ReviewException(ReviewExceptionType.REVIEW_NOT_FOUND));

        Reservation savedReservation = reservationPersist.findByReservationId(savedGroomingReview.getReservationId())
                .orElseThrow(() -> new ReservationException(ReservationExceptionType.RESERVATION_NOT_FOUND));

        return GroomingReviewDetailResp.builder()
                .groomingReviewId(savedGroomingReview.getGroomingReviewId())
                .groomerId(savedGroomingReview.getGroomerId())
                .groomingKeywordReviewList(savedGroomingReview.getGroomingKeywordReviewList())
                .revieweeName(savedGroomingReview.getRevieweeName())
                .shopName(savedGroomingReview.getShopName())
                .starRating(savedGroomingReview.getStarRating())
                .schedule(savedReservation.getSchedule())
                .content(savedGroomingReview.getContent())
                .imageUrlList(savedGroomingReview.getImageUrlList())
                .build();
    }

    @Transactional
    public ReviewResp postReview(PostGroomingReviewInfo postGroomingReviewInfo) {
        Reservation reservation = reservationPersist.findByReservationId(postGroomingReviewInfo.getReservationId()).orElseThrow(()
                -> new ReservationException(ReservationExceptionType.RESERVATION_NOT_FOUND));

        if(isContainBanWord(postGroomingReviewInfo.getContent())) throw new ReviewException(ReviewExceptionType.REVIEW_CONTENT_CONTAIN_BAN_WORD);
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

    @Transactional
    public ReviewResp updateReview(Long reviewId, UpdateGroomingReviewInfo updateGroomingReviewInfo) {
        GroomingReview savedGroomingReview = groomingReviewPersist.findByReviewId(reviewId)
                .orElseThrow(() -> new ReviewException(ReviewExceptionType.REVIEW_NOT_FOUND));

        if(isContainBanWord(updateGroomingReviewInfo.getContent())) throw new ReviewException(ReviewExceptionType.REVIEW_CONTENT_CONTAIN_BAN_WORD);

        validateModifyGroomingReviewInfoDataFormat(updateGroomingReviewInfo);

        GroomingReview modifiedReview = GroomingReviewMapper.updateBy(savedGroomingReview, updateGroomingReviewInfo);
        GroomingReview updatedGroomingReview = groomingReviewPersist.save(modifiedReview);

        //TODO 댕글미터 재계산해서 영속

        return ReviewResp.builder()
                .reviewId(updatedGroomingReview.getGroomingReviewId())
                .reviewerId(updatedGroomingReview.getReviewerId())
                .revieweeId(updatedGroomingReview.getGroomerId())
                .build();
    }

    @Transactional
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

    @Transactional(readOnly = true)
    public GroomingReviewListResp findMyReviewList(Long accountId, int page, int size) {
        User savedUser = userPersist.findByAccountId(accountId)
                .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));

        Pageable pageable = PageRequest.of(page, size);
        Page<GroomingReview> groomingReviews = groomingReviewPersist.findByReviewerId(savedUser.getAccountId(), pageable);

        return mappingToGroomingReviewListResp(groomingReviews);
    }

    @Transactional(readOnly = true)
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

    private void validateModifyGroomingReviewInfoDataFormat(UpdateGroomingReviewInfo updateGroomingReviewInfo) {
        GroomingReview.validateStarRating(updateGroomingReviewInfo.getStarRating());
        GroomingReview.validateGroomingKeywordReviewList(updateGroomingReviewInfo.getGroomingKeywordReviewList());
        GroomingReview.validateContent(updateGroomingReviewInfo.getContent());
        GroomingReview.validateImageUrlList(updateGroomingReviewInfo.getImageUrlList());
    }

    private GroomingReviewListResp mappingToGroomingReviewListResp(Page<GroomingReview> groomingReviews) {
        List<GroomingReviewSummaryResp> groomingReviewList = groomingReviews.stream().map(groomingReview -> {

            User reviewer = userPersist.findByAccountId(groomingReview.getReviewerId())
                    .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));

            return GroomingReviewSummaryResp.builder()
                    .groomingReviewId(groomingReview.getGroomingReviewId())
                    .reviewerName(reviewer.getUsername())
                    .reviewerImageUrl(reviewer.getUserImage())
                    .groomerId(groomingReview.getGroomerId())
                    .groomingKeywordReviewList(groomingReview.getGroomingKeywordReviewList())
                    .revieweeName(groomingReview.getRevieweeName())
                    .starRating(groomingReview.getStarRating())
                    .content(groomingReview.getContent())
                    .imageUrlList(groomingReview.getImageUrlList())
                    .build();
        }).toList(); // groomingReviewList

        return GroomingReviewListResp.builder()
                .reviewCount(groomingReviews.getTotalElements())
                .reviewList(groomingReviewList)
                .build();
    }

    private boolean isContainBanWord(String content) {
        String filteredContent = badWordFiltering.change(content, new String[] {"_",",",".","!","?","@","1","2","3","4","5","6","7","8","9","0"," "});
        return !content.equals(filteredContent);
    }
}
