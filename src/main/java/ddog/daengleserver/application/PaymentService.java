package ddog.daengleserver.application;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import ddog.daengleserver.application.repository.OrderRepository;
import ddog.daengleserver.application.repository.PaymentRepository;
import ddog.daengleserver.domain.Order;
import ddog.daengleserver.domain.Payment;
import ddog.daengleserver.domain.enums.OrderExceptionType;
import ddog.daengleserver.domain.enums.PaymentExceptionType;
import ddog.daengleserver.domain.exception.OrderException;
import ddog.daengleserver.domain.exception.PaymentException;
import ddog.daengleserver.presentation.dto.request.PaymentCallbackReq;
import ddog.daengleserver.presentation.dto.response.PaymentCallbackResp;
import ddog.daengleserver.presentation.usecase.PaymentUseCase;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService implements PaymentUseCase {

    private final IamportClient iamportClient;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    @CircuitBreaker(name = "paymentValidation", fallbackMethod = "fallbackValidationPayment")
    public PaymentCallbackResp validationPayment(PaymentCallbackReq paymentCallbackReq) {
        return executePaymentValidation(paymentCallbackReq);
    }

    private PaymentCallbackResp executePaymentValidation(PaymentCallbackReq paymentCallbackReq) {
        Order order = orderRepository.findBy(paymentCallbackReq.getOrderUid())
                .orElseThrow(() -> new OrderException(OrderExceptionType.ORDER_NOT_FOUNDED));
        Payment payment = order.getPayment();

        try {
            com.siot.IamportRestClient.response.Payment iamportResp =
                    iamportClient.paymentByImpUid(paymentCallbackReq.getPaymentUid()).getResponse();

            validatePaymentStatus(payment, iamportResp);
            validatePaymentAmount(payment, iamportResp);

            payment.validationSuccess(iamportResp.getImpUid());
            return createSuccessResponse(order, payment);

        } catch (IamportResponseException | IOException e) {
            throw new PaymentException(PaymentExceptionType.PAYMENT_PG_INTEGRATION_FAILED);
        }
    }

    private void fallbackValidationPayment(PaymentCallbackReq paymentCallbackReq, Throwable throwable) {
        log.error("Circuit breaker activated for order {}: {}", paymentCallbackReq.getOrderUid(), throwable.getMessage());

        throw new PaymentException(PaymentExceptionType.PAYMENT_PG_SYSTEM_TIMEOUT);
    }

    private void validatePaymentStatus(Payment payment, com.siot.IamportRestClient.response.Payment iamportResp) {
        if (payment.checkIncompleteBy(iamportResp.getStatus())) {
            payment.cancel();
            paymentRepository.save(payment);
            throw new PaymentException(PaymentExceptionType.PAYMENT_PG_INCOMPLETE);
        }
    }

    private void validatePaymentAmount(Payment payment, com.siot.IamportRestClient.response.Payment iamportResp) throws IamportResponseException, IOException {
        long paymentAmount = iamportResp.getAmount().longValue();
        if (payment.checkInValidationBy(paymentAmount)) {
            payment.cancel();
            paymentRepository.save(payment);
            iamportClient.cancelPaymentByImpUid(
                    new CancelData(iamportResp.getImpUid(), true, new BigDecimal(paymentAmount))
            );
            throw new PaymentException(PaymentExceptionType.PAYMENT_PG_AMOUNT_MISMATCH);
        }
    }

    private PaymentCallbackResp createSuccessResponse(Order order, Payment payment) {
        return PaymentCallbackResp.builder()
                .userId(order.getAccountId())
                .paymentId(payment.getPaymentId())
                .price(payment.getPrice())
                .build();
    }
}