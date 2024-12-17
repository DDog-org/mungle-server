package ddog.persistence.mysql.adapter;

import ddog.domain.payment.Order;
import ddog.persistence.mysql.jpa.entity.OrderJpaEntity;
import ddog.persistence.mysql.jpa.repository.OrderJpaRepository;
import ddog.domain.payment.port.OrderPersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class OrderRepository implements OrderPersist {

    private final OrderJpaRepository orderJpaRepository;

    @Override
    public Order save(Order order) {
        return orderJpaRepository.save(OrderJpaEntity.fromModel(order)).toModel();
    }

    @Override
    public Optional<Order> findByOrderUid(String orderUid) {
        return orderJpaRepository.findByOrderUid(orderUid).map(OrderJpaEntity::toModel);
    }

    @Override
    public void delete(Order order) {
        orderJpaRepository.delete(OrderJpaEntity.fromModel(order));
    }

    @Override
    public Optional<Order> findByIdempotencyKey(String idempotencyKey) {
        return orderJpaRepository.findByIdempotencyKey(idempotencyKey).map(OrderJpaEntity::toModel);
    }
}
