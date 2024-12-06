package ddog.payment.application;

import ddog.domain.payment.Order;
import ddog.domain.payment.dto.PostOrderInfo;
import ddog.domain.payment.Payment;
import ddog.payment.application.dto.response.PostOrderResp;
import ddog.persistence.mysql.port.OrderPersist;
import ddog.persistence.mysql.port.PaymentPersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderPersist orderPersist;
    private final PaymentPersist paymentPersist;

    @Transactional
    public PostOrderResp processOrder(Long accountId, PostOrderInfo postOrderInfo) {
        Payment paymentToSave = Payment.createTemporaryHistoryBy(accountId, postOrderInfo);
        Payment SavedPayment = paymentPersist.save(paymentToSave);

        Order order = Order.createBy(accountId, postOrderInfo, SavedPayment);
        orderPersist.save(order);

        return PostOrderResp.builder()
                .accountId(accountId)
                .estimateId(postOrderInfo.getEstimateId())
                .orderUId(order.getOrderUid())
                .build();
    }
}
