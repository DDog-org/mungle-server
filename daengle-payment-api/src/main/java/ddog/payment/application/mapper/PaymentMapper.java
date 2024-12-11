package ddog.payment.application.mapper;

import ddog.domain.payment.Payment;
import ddog.domain.payment.enums.PaymentStatus;
import ddog.payment.presentation.dto.PostOrderInfo;

import java.time.LocalDateTime;

public class PaymentMapper {

    public static Payment createTemporaryHistoryBy(Long accountId, PostOrderInfo postOrderInfo) {
        return Payment.builder()
                .paymentId(null)
                .payerId(accountId)
                .price(postOrderInfo.getPrice())
                .status(PaymentStatus.PAYMENT_READY)
                .paymentDate(LocalDateTime.now())
                .paymentUid(null)
                .build();
    }
}
