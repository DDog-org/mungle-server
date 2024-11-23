package ddog.daengleserver.application.repository;

import ddog.daengleserver.domain.Order;

public interface OrderRepository {
    void save(Order order);
}
