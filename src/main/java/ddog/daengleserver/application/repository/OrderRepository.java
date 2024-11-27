package ddog.daengleserver.application.repository;

import ddog.daengleserver.domain.Order;

import java.util.Optional;

public interface OrderRepository {
    void save(Order order);
    Optional<Order> findBy(String orderUid);
    void delete(Order order);
}
