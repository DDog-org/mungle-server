package ddog.daengleserver.application;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import ddog.daengleserver.application.repository.OrderRepository;
import ddog.daengleserver.application.repository.PaymentRepository;
import ddog.daengleserver.domain.Order;
import ddog.daengleserver.domain.Payment;
import ddog.daengleserver.domain.enums.PaymentExceptionType;
import ddog.daengleserver.domain.exception.PaymentException;
import ddog.daengleserver.presentation.dto.request.PaymentCallbackReq;
import ddog.daengleserver.presentation.dto.response.PaymentCallbackResp;
import ddog.daengleserver.presentation.usecase.PaymentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentService implements PaymentUseCase {

    public static final String PAYMENT_SUCCESS_STATUS = "paid";
    private final IamportClient iamportClient;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public PaymentCallbackResp validationPayment(PaymentCallbackReq paymentCallbackReq) {
        Order order = orderRepository.findBy(paymentCallbackReq.getOrderUid());
        Payment payment = order.getPayment();

        try {
            com.siot.IamportRestClient.response.Payment iamportResp =
                    iamportClient.paymentByImpUid(paymentCallbackReq.getPaymentUid()).getResponse();
            String paymentStatus = iamportResp.getStatus();

            //결제 완료 처리 확인       TODO 도메인 객체에 역할 위임
            if(paymentStatus != PAYMENT_SUCCESS_STATUS) {
                payment.cancel();
                paymentRepository.save(order.getPayment());

                throw new PaymentException(PaymentExceptionType.PAYMENT_PG_INCOMPLETE);
            }

            //결제 금액 검증      TODO 도메인 객체에 역할 위임
            Long paymentAmount = iamportResp.getAmount().longValue();
            if(paymentAmount != payment.getPrice()) {
                payment.cancel();
                paymentRepository.save(order.getPayment());
                iamportClient.cancelPaymentByImpUid(new CancelData(iamportResp.getImpUid(), true, new BigDecimal(paymentAmount)));

                throw new PaymentException(PaymentExceptionType.PAYMENT_PG_AMOUNT_MISMATCH);
            }

            //결제 검증 절차 성공
            payment.validationSuccess(iamportResp.getImpUid());

            return PaymentCallbackResp.builder()
                    .userId(order.getUserId())
                    .paymentId(payment.getPaymentId())
                    .price(payment.getPrice())
                    .build();

        } catch (IamportResponseException | IOException e) {
            throw new PaymentException(PaymentExceptionType.PAYMENT_PG_INTEGRATION_FAILED);
        }
    }
}
