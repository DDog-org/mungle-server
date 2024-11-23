package ddog.daengleserver.application;

import ddog.daengleserver.application.repository.OrderRepository;
import ddog.daengleserver.domain.Order;
import ddog.daengleserver.presentation.dto.request.PostOrderReq;
import ddog.daengleserver.presentation.usecase.OrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderUseCase {

    private final OrderRepository orderRepository;

    @Transactional
    public void registerOrder(PostOrderReq postOrderReq) {
        orderRepository.save(Order.createNewOrderBy(postOrderReq));
    }
}
