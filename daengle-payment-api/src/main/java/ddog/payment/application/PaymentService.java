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
import ddog.domain.user.User;
import ddog.domain.user.port.UserPersist;
import ddog.payment.application.dto.request.PaymentCallbackReq;
import ddog.payment.application.dto.response.PaymentCallbackResp;
import ddog.payment.application.dto.response.PaymentHistoryListResp;
import ddog.payment.application.dto.response.PaymentHistorySummaryResp;
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

    @Transactional
    public PaymentCallbackResp validationPayment(PaymentCallbackReq paymentCallbackReq) {
        Order order = orderPersist.findByOrderUid(paymentCallbackReq.getOrderUid()).orElseThrow(() -> new OrderException(OrderExceptionType.ORDER_NOT_FOUNDED));
        Payment payment = order.getPayment();

        validateEstimate(order);

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

    //TODO Estimate 도메인 객체에게 역할 위임하기 확장성이 있는 유효성 검사 로직을 구현하기 (언제든 새로운 00견적 서비스가 추가될 수 있다)
    private void validateEstimate(Order order) {
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

    public PaymentHistoryListResp findGroomingPaymentHistory(Long accountId, int page, int size) {
        User savedUser = userPersist.findByAccountId(accountId)
                .orElseThrow(() -> new PaymentException(PaymentExceptionType.PAYMENT_USER_NOT_FOUND));

        Pageable pageable = PageRequest.of(page, size);
        Page<Reservation> reservations = reservationPersist.findGroomingPaymentHistory(savedUser.getAccountId(), pageable);

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
}
