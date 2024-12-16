package ddog.payment.application;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import ddog.domain.estimate.CareEstimate;
import ddog.domain.estimate.EstimateStatus;
import ddog.domain.estimate.GroomingEstimate;
import ddog.domain.estimate.port.CareEstimatePersist;
import ddog.domain.estimate.port.GroomingEstimatePersist;
import ddog.domain.payment.Order;
import ddog.domain.payment.Payment;
import ddog.domain.payment.Reservation;
import ddog.domain.payment.enums.ServiceType;
import ddog.domain.payment.port.OrderPersist;
import ddog.domain.payment.port.PaymentPersist;
import ddog.domain.payment.port.ReservationPersist;
import ddog.domain.review.CareReview;
import ddog.domain.review.GroomingReview;
import ddog.domain.review.port.CareReviewPersist;
import ddog.domain.review.port.GroomingReviewPersist;
import ddog.domain.user.User;
import ddog.domain.user.port.UserPersist;
import ddog.payment.application.dto.request.PaymentCallbackReq;
import ddog.payment.application.dto.response.*;
import ddog.payment.application.exception.*;
import ddog.payment.application.mapper.ReservationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final IamportClient iamportClient;

    private final UserPersist userPersist;
    private final OrderPersist orderPersist;
    private final PaymentPersist paymentPersist;
    private final ReservationPersist reservationPersist;

    private final GroomingEstimatePersist groomingEstimatePersist;
    private final CareEstimatePersist careEstimatePersist;

    private final CareReviewPersist careReviewPersist;
    private final GroomingReviewPersist groomingReviewPersist;

    @Transactional
    public PaymentCallbackResp validationPayment(PaymentCallbackReq paymentCallbackReq) {
        Order order = orderPersist.findByOrderUid(paymentCallbackReq.getOrderUid()).orElseThrow(() -> new OrderException(OrderExceptionType.ORDER_NOT_FOUNDED));
        Payment payment = order.getPayment();

        validateEstimateBy(order);

        try {
            com.siot.IamportRestClient.response.Payment iamportResp =
                    iamportClient.paymentByImpUid(paymentCallbackReq.getPaymentUid()).getResponse();
            String paymentStatus = iamportResp.getStatus();
            long paymentAmount = iamportResp.getAmount().longValue();

            //결제 완료 검증
            if (payment.checkIncompleteBy(paymentStatus)) {  //TODO 결제상태 변경과 영속도 도메인 엔티티에게 위임하기
                payment.invalidate();
                paymentPersist.save(payment);

                throw new PaymentException(PaymentExceptionType.PAYMENT_PG_INCOMPLETE);
            }

            //결제 금액 검증
            if (payment.checkInValidationBy(paymentAmount)) {    //TODO 결제상태 변경과 영속도 도메인 엔티티에게 위임하기
                payment.invalidate();
                paymentPersist.save(payment);

                iamportClient.cancelPaymentByImpUid(new CancelData(iamportResp.getImpUid(), true, new BigDecimal(paymentAmount)));
                throw new PaymentException(PaymentExceptionType.PAYMENT_PG_AMOUNT_MISMATCH);
            }

            //결제 검증 절차 성공
            payment.validationSuccess(iamportResp.getImpUid());
            paymentPersist.save(payment);

            //예약 정보 생성
            Reservation reservationToSave = ReservationMapper.createBy(order, payment);
            Reservation savedReservation = reservationPersist.save(reservationToSave);

            return PaymentCallbackResp.builder()
                    .customerId(order.getAccountId())
                    .reservationId(savedReservation.getReservationId())
                    .paymentId(payment.getPaymentId())
                    .price(payment.getPrice())
                    .build();

        } catch (IamportResponseException | IOException e) {
            throw new PaymentException(PaymentExceptionType.PAYMENT_PG_INTEGRATION_FAILED);
        }
    }

    @Transactional
    public PaymentCancelResp cancelPayment(Long reservationId) {
        Reservation savedReservation = reservationPersist.findByReservationId(reservationId)
                .orElseThrow(() -> new PaymentException(PaymentExceptionType.PAYMENT_RESERVATION_NOT_FOUND));

        Payment savedPayment = paymentPersist.findByPaymentId(savedReservation.getPaymentId())
                .orElseThrow(() -> new PaymentException(PaymentExceptionType.PAYMENT_NOT_FOUND));

        BigDecimal refundAmount = savedPayment.calculateRefundAmount(savedReservation.getSchedule());
        CancelData cancel_data = new CancelData(savedPayment.getPaymentUid(), true, refundAmount);

        try {
            if(refundAmount.compareTo(BigDecimal.ZERO) > 0) iamportClient.cancelPaymentByImpUid(cancel_data);

            savedPayment.cancel();
            paymentPersist.save(savedPayment);

            return PaymentCancelResp.builder()
                    .paymentId(savedPayment.getPaymentId())
                    .reservationId(savedReservation.getReservationId())
                    .originDepositAmount(savedPayment.getPrice())
                    .refundAmount(refundAmount)
                    .build();

        } catch (IamportResponseException | IOException e) {
            throw new PaymentException(PaymentExceptionType.PAYMENT_PG_INTEGRATION_FAILED);
        }
    }

    @Transactional
    public List<PaymentCancelResp> cancelPayments(List<Long> reservationIds) {
        try {
            return reservationIds.stream()
                    .map(this::cancelPayment) // cancelPayment 호출
                    .collect(Collectors.toList()); // 모든 결과 수집
        } catch (Exception e) {
            // 예외 발생 시 전체 작업 롤백
            throw new PaymentException(PaymentExceptionType.PAYMENT_CANCEL_BATCH_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public PaymentHistoryDetail getPaymentHistory(Long reservationId) {
        Reservation savedReservation = reservationPersist.findByReservationId(reservationId)
                .orElseThrow(() -> new PaymentException(PaymentExceptionType.PAYMENT_HISTORY_NOT_FOUND));

        Boolean hasWrittenReview = checkIfReviewExistsBy(savedReservation);

        return PaymentHistoryDetail.builder()
                .reservationId(savedReservation.getReservationId())
                .reservationStatus(savedReservation.getReservationStatus())
                .recipientName(savedReservation.getRecipientName())
                .shopName(savedReservation.getShopName())
                .schedule(savedReservation.getSchedule())
                .deposit(savedReservation.getDeposit())
                .customerName(savedReservation.getCustomerName())
                .customerPhoneNumber(savedReservation.getCustomerPhoneNumber())
                .visitorName(savedReservation.getVisitorName())
                .visitorPhoneNumber(savedReservation.getVisitorPhoneNumber())
                .hasWrittenReview(hasWrittenReview)
                .build();
    }

    @Transactional(readOnly = true)
    public PaymentHistoryListResp findPaymentHistoryList(Long accountId, ServiceType serviceType, int page, int size) {
        User savedUser = userPersist.findByAccountId(accountId)
                .orElseThrow(() -> new PaymentException(PaymentExceptionType.PAYMENT_USER_NOT_FOUND));

        Pageable pageable = PageRequest.of(page, size);
        Page<Reservation> reservations = reservationPersist.findPaymentHistoryList(savedUser.getAccountId(), serviceType, pageable);

        return mappingToPaymentHistoryListResp(reservations);
    }

    private PaymentHistoryListResp mappingToPaymentHistoryListResp(Page<Reservation> reservations) {
        List<PaymentHistorySummaryResp> paymentHistoryList = reservations.stream().map(reservation -> PaymentHistorySummaryResp.builder()
                .reservationId(reservation.getReservationId())
                .recipientImageUrl(reservation.getRecipientImageUrl())
                .recipientName(reservation.getRecipientName())
                .shopName(reservation.getShopName())
                .paymentDate(reservation.getSchedule())
                .status(reservation.getReservationStatus().name())
                .build()).collect(Collectors.toList());

        return PaymentHistoryListResp.builder()
                .paymentHistoryList(paymentHistoryList)
                .build();
    }

    //TODO Estimate 도메인 객체에게 역할 위임하기, 확장성이 있는 유효성 검사 로직을 구현하기 (언제든 새로운 00견적 서비스가 추가될 수 있다)
    private void validateEstimateBy(Order order) {
        if (order.getServiceType().equals(ServiceType.GROOMING)) {
            GroomingEstimate estimate = groomingEstimatePersist.findByEstimateId(order.getEstimateId())
                    .orElseThrow(() -> new GroomingEstimateException(GroomingEstimateExceptionType.GROOMING_ESTIMATE_NOT_FOUND));

            groomingEstimatePersist.updateStatusWithParentId(EstimateStatus.END, estimate.getParentId());

            estimate.updateStatus(EstimateStatus.ON_RESERVATION);
            groomingEstimatePersist.save(estimate);

        } else if (order.getServiceType().equals(ServiceType.CARE)) {
            CareEstimate estimate = careEstimatePersist.findByEstimateId(order.getEstimateId())
                    .orElseThrow(() -> new CareEstimateException(CareEstimateExceptionType.CARE_ESTIMATE_NOT_FOUND));

            careEstimatePersist.updateStatusWithParentId(EstimateStatus.END, estimate.getParentId());

            estimate.updateStatus(EstimateStatus.ON_RESERVATION);
            careEstimatePersist.save(estimate);

        } else {
            throw new IllegalArgumentException("서비스 타입이 올바르지 않습니다.");
        }
    }

    //TODO Review 도메인 객체에게 역할 위임하기, 확장성이 있는 검사 로직을 구현하기 (언제든 새로운 00리뷰 서비스가 추가될 수 있다)
    private Boolean checkIfReviewExistsBy(Reservation reservation) {
        Long reviewerId = reservation.getCustomerId();
        Long reservationId = reservation.getReservationId();

        if (reservation.getServiceType().equals(ServiceType.GROOMING)) {
            return groomingReviewPersist.findByReviewerIdAndReservationId(reviewerId, reservationId).isPresent();

        } else if (reservation.getServiceType().equals(ServiceType.CARE)) {
            return careReviewPersist.findByReviewerIdAndReservationId(reviewerId, reservationId).isPresent();
        }
        return false;
    }
}
