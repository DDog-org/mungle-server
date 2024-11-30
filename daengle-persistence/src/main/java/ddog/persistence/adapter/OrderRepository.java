package ddog.persistence.adapter;

import ddog.domain.order.Order;
import ddog.persistence.jpa.entity.OrderJpaEntity;
import ddog.persistence.jpa.repository.OrderJpaRepository;
import ddog.persistence.port.OrderPersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class OrderRepository implements OrderPersist {

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
