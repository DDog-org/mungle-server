package ddog.payment.application;

import ddog.domain.order.Order;
import ddog.domain.order.enums.PostOrderReq;
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
    public PostOrderResp processOrder(Long accountId, PostOrderReq postOrderReq) {
        Payment paymentToSave = Payment.createTemporaryHistoryBy(postOrderReq);
        Payment SavedPayment = paymentPersist.save(paymentToSave);

        Order order = Order.createBy(accountId, postOrderReq, SavedPayment);
        orderPersist.save(order);

        return PostOrderResp.builder()
                .accountId(accountId)
                .itemId(postOrderReq.getItemId())
                .orderUId(order.getOrderUid())
                .build();
    }
}
