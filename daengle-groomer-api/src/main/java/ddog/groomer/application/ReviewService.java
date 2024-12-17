package ddog.groomer.application;

import ddog.domain.groomer.Groomer;
import ddog.domain.payment.Reservation;
import ddog.domain.payment.port.ReservationPersist;
import ddog.domain.review.GroomingReview;
import ddog.domain.review.ReportedReview;
import ddog.domain.review.port.ReportedReviewPersist;
import ddog.domain.user.User;
import ddog.domain.user.port.UserPersist;
import ddog.groomer.application.exception.GroomingReviewException;
import ddog.groomer.application.exception.GroomingReviewExceptionType;
import ddog.groomer.application.exception.account.GroomerException;
import ddog.groomer.application.exception.account.GroomerExceptionType;
import ddog.groomer.application.exception.account.UserException;
import ddog.groomer.application.exception.account.UserExceptionType;
import ddog.groomer.application.mapper.ReportReviewMapper;
import ddog.groomer.presentation.review.dto.*;
import ddog.domain.groomer.port.GroomerPersist;
import ddog.domain.review.port.GroomingReviewPersist;
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
public class ReviewService {

    private final GroomingReviewPersist groomingReviewPersist;
    private final ReportedReviewPersist reportedReviewPersist;
    private final ReservationPersist reservationPersist;


    private final UserPersist userPersist;
    private final GroomerPersist groomerPersist;

    @Transactional(readOnly = true)
    public ReviewListResp findReviewList(Long accountId, int page, int size) {
        Groomer savedGroomer = groomerPersist.findByAccountId(accountId)
                .orElseThrow(() -> new GroomerException(GroomerExceptionType.GROOMER_NOT_FOUND));

        Pageable pageable = PageRequest.of(page, size);
        Page<GroomingReview> groomingReviews = groomingReviewPersist.findByGroomerId(savedGroomer.getGroomerId(), pageable);

        return mappingToGroomingReviewListResp(groomingReviews);
    }

    @Transactional(readOnly = true)
    public ReportReviewResp reportReview(Long groomingReviewId) {
        GroomingReview savedGroomingReview = groomingReviewPersist.findByReviewId(groomingReviewId)
                .orElseThrow(() -> new GroomingReviewException(GroomingReviewExceptionType.GROOMING_REVIEW_NOT_FOUND));

        User reviewer = userPersist.findByAccountId(savedGroomingReview.getReviewerId())
                .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));

        return ReportReviewResp.builder()
                .reviewerNickName(reviewer.getNickname())
                .reviewerImageUrl(reviewer.getImageUrl())
                .build();
    }

    @Transactional
    public SubmitReportReviewResp reportReview(ReportReviewReq reportReviewReq) {
        groomerPersist.findByGroomerId(reportReviewReq.getGroomerId())
                .orElseThrow(() -> new GroomerException(GroomerExceptionType.GROOMER_NOT_FOUND));

        GroomingReview savedGroomingReview = groomingReviewPersist.findByReviewId(reportReviewReq.getReviewId())
                .orElseThrow(() -> new GroomingReviewException(GroomingReviewExceptionType.GROOMING_REVIEW_NOT_FOUND));

        ReportedReview reportReviewToSave = ReportReviewMapper.create(savedGroomingReview, reportReviewReq);
        ReportedReview savedReportReview = reportedReviewPersist.save(reportReviewToSave);

        return SubmitReportReviewResp.builder()
                .reviewId(savedReportReview.getReportedReviewId())
                .reviewerId(savedReportReview.getReviewerId())
                .revieweeId(savedReportReview.getReporterId())
                .build();
    }

    @Transactional(readOnly = true)
    public ReportedReviewListResp findReportedReviewList(Long accountId, int page, int size) {
        Groomer savedGroomer = groomerPersist.findByAccountId(accountId)
                .orElseThrow(() -> new GroomerException(GroomerExceptionType.GROOMER_NOT_FOUND));

        Pageable pageable = PageRequest.of(page, size);
        Page<ReportedReview> reportedReviews = reportedReviewPersist.findByReporterId(savedGroomer.getGroomerId(), pageable);

        return mappingToReportedReviewListResp(reportedReviews);
    }

    private ReviewListResp mappingToGroomingReviewListResp(Page<GroomingReview> groomingReviews) {
        List<ReviewSummaryResp> groomingReviewList = groomingReviews.stream().map(groomingReview -> {

            User reviewer = userPersist.findByAccountId(groomingReview.getReviewerId())
                    .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));

            Reservation savedReservation = reservationPersist.findByReservationId(groomingReview.getReservationId())
                    .orElseThrow(() -> new GroomingReviewException(GroomingReviewExceptionType.GROOMING_REVIEW_RESERVATION_NOT_FOUND));

            return ReviewSummaryResp.builder()
                    .groomingReviewId(groomingReview.getGroomingReviewId())
                    .reviewerName(reviewer.getNickname())
                    .reviewerImageUrl(reviewer.getImageUrl())
                    .groomerId(groomingReview.getGroomerId())
                    .groomingKeywordList(groomingReview.getGroomingKeywordList())
                    .revieweeName(groomingReview.getRevieweeName())
                    .createdAt(savedReservation.getSchedule())
                    .starRating(groomingReview.getStarRating())
                    .content(groomingReview.getContent())
                    .imageUrlList(groomingReview.getImageUrlList())
                    .build();
        }).toList();    //groomingReviewList

        return ReviewListResp.builder()
                .reviewCount(groomingReviews.getTotalElements())
                .reviewList(groomingReviewList)
                .build();
    }

    private ReportedReviewListResp mappingToReportedReviewListResp(Page<ReportedReview> reportedReviews) {
        List<ReportedReviewSummaryResp> reportedReviewList = reportedReviews.stream().map(reportedReview -> {

            GroomingReview groomingReview = groomingReviewPersist.findByReviewId(reportedReview.getReportedReviewId())
                    .orElseThrow(() -> new GroomingReviewException(GroomingReviewExceptionType.GROOMING_REVIEW_NOT_FOUND));

            User reviewer = userPersist.findByAccountId(reportedReview.getReviewerId())
                    .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));

            return ReportedReviewSummaryResp.builder()
                    .groomingReviewId(groomingReview.getGroomingReviewId())
                    .reviewerName(reviewer.getNickname())
                    .reviewerImageUrl(reviewer.getImageUrl())
                    .groomerId(groomingReview.getGroomerId())
                    .groomingKeywordList(groomingReview.getGroomingKeywordList())
                    .revieweeName(groomingReview.getRevieweeName())
                    .createdAt(groomingReview.getCreatedAt())
                    .starRating(groomingReview.getStarRating())
                    .content(groomingReview.getContent())
                    .imageUrlList(groomingReview.getImageUrlList())
                    .reportType(reportedReview.getReportType())
                    .reportContent(reportedReview.getReportContent())
                    .build();
        }).toList();

        return ReportedReviewListResp.builder()
                .reviewCount(reportedReviews.getTotalElements())
                .reviewList(reportedReviewList)
                .build();
    }
}
