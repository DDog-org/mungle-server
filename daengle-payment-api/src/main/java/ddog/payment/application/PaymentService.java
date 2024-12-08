package ddog.payment.application;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import ddog.domain.payment.Order;
import ddog.domain.payment.Payment;
import ddog.domain.payment.Reservation;
import ddog.domain.payment.enums.ReservationStatus;
import ddog.payment.application.dto.request.PaymentCallbackReq;
import ddog.payment.application.dto.response.PaymentCallbackResp;
import ddog.payment.application.exception.OrderException;
import ddog.payment.application.exception.OrderExceptionType;
import ddog.payment.application.exception.PaymentException;
import ddog.payment.application.exception.PaymentExceptionType;
import ddog.persistence.mysql.port.OrderPersist;
import ddog.persistence.mysql.port.PaymentPersist;
import ddog.persistence.mysql.port.ReservationPersist;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final IamportClient iamportClient;
    private final OrderPersist orderPersist;
    private final PaymentPersist paymentPersist;
    private final ReservationPersist reservationPersist;

    @Transactional
    public PaymentCallbackResp validationPayment(PaymentCallbackReq paymentCallbackReq) {
        Order order = orderPersist.findBy(paymentCallbackReq.getOrderUid()).orElseThrow(() -> new OrderException(OrderExceptionType.ORDER_NOT_FOUNDED));
        Payment payment = order.getPayment();
        //TODO 견적 데이터 유효성 검사 로직

        try {
            com.siot.IamportRestClient.response.Payment iamportResp =
                    iamportClient.paymentByImpUid(paymentCallbackReq.getPaymentUid()).getResponse();
            String paymentStatus = iamportResp.getStatus();
            long paymentAmount = iamportResp.getAmount().longValue();

            //결제 완료 검증
            if(payment.checkIncompleteBy(paymentStatus)) {  //TODO 결제상태 변경과 영속도 도메인 엔티티에게 위임하기
                payment.cancel();
                paymentPersist.save(payment);

                throw new PaymentException(PaymentExceptionType.PAYMENT_PG_INCOMPLETE);
            }

            //결제 금액 검증
            if(payment.checkInValidationBy(paymentAmount)) {    //TODO 결제상태 변경과 영속도 도메인 엔티티에게 위임하기
                payment.cancel();
                paymentPersist.save(payment);
                iamportClient.cancelPaymentByImpUid(new CancelData(iamportResp.getImpUid(), true, new BigDecimal(paymentAmount)));

                throw new PaymentException(PaymentExceptionType.PAYMENT_PG_AMOUNT_MISMATCH);
            }

            //결제 검증 절차 성공
            payment.validationSuccess(iamportResp.getImpUid());
            paymentPersist.save(payment);

            //예약 정보 생성
            Reservation reservation = Reservation.builder()
                    .estimateId(order.getEstimateId())
                    .serviceType(order.getServiceType())
                    .reservationStatus(ReservationStatus.DEPOSIT_PAID)
                    .recipientId(order.getRecipientId())  //수의사 or 병원 PK
                    .recipientName(order.getRecipientName())
                    .shopName(order.getShopName())
                    .schedule(order.getSchedule())
                    .deposit(order.getPrice())
                    .customerId(order.getAccountId())
                    .customerPhoneNumber(order.getCustomerPhoneNumber())
                    .visitorName(order.getVisitorName())
                    .visitorPhoneNumber(order.getVisitorPhoneNumber())
                    .paymentId(payment.getPaymentId())
                    .build();
            reservation = reservationPersist.save(reservation);

            return PaymentCallbackResp.builder()
                    .customerId(order.getAccountId())
                    .reservationId(reservation.getReservationId())
                    .paymentId(payment.getPaymentId())
                    .price(payment.getPrice())
                    .build();

        } catch (IamportResponseException | IOException e) {
            throw new PaymentException(PaymentExceptionType.PAYMENT_PG_INTEGRATION_FAILED);
        }
    }
}
