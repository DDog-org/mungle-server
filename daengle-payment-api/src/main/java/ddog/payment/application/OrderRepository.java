package ddog.payment.application;

import ddog.daengleserver.domain.Order;

import java.util.Optional;

public interface OrderRepository {  //TODO persist로 접미사 변경
    void save(Order order);
    Optional<Order> findBy(String orderUid);
    void delete(Order order);
}
