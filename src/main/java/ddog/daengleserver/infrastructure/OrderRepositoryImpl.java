package ddog.daengleserver.infrastructure;

import ddog.daengleserver.application.repository.OrderRepository;
import ddog.daengleserver.domain.Order;
import ddog.daengleserver.domain.enums.OrderExceptionType;
import ddog.daengleserver.domain.exception.OrderException;
import ddog.daengleserver.infrastructure.po.OrderJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;

    @Override
    public void save(Order order) {
        orderJpaRepository.save(OrderJpaEntity.fromModel(order));
    }

    @Override
    public Optional<Order> findBy(String orderUid) {
        return orderJpaRepository.findByOrderUid(orderUid).map(OrderJpaEntity::toModel);
    }

    @Override
    public void delete(Order order) {
        orderJpaRepository.delete(OrderJpaEntity.fromModel(order));
    }
}
