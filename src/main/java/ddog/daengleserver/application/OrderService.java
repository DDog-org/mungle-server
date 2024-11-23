package ddog.daengleserver.application;

import ddog.daengleserver.application.repository.OrderRepository;
import ddog.daengleserver.application.repository.PaymentRepository;
import ddog.daengleserver.domain.Order;
import ddog.daengleserver.domain.Payment;
import ddog.daengleserver.presentation.dto.request.PostOrderReq;
import ddog.daengleserver.presentation.usecase.OrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderUseCase {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    @Transactional
    public void processOrder(PostOrderReq postOrderReq) {
        Payment payment = Payment.saveTemporaryHistoryBy(postOrderReq);
        paymentRepository.save(payment);

        Order order = Order.createBy(postOrderReq, payment);
        orderRepository.save(order);
    }
}
