package ddog.daengleserver.infrastructure;

import ddog.daengleserver.application.repository.OrderRepository;
import ddog.daengleserver.domain.Order;
import ddog.daengleserver.infrastructure.po.OrderJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;

    @Override
    public void save(Order order) {
        orderJpaRepository.save(OrderJpaEntity.fromModel(order));
    }
}
