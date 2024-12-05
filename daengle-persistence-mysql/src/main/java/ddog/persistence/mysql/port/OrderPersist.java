package ddog.persistence.mysql.port;

import ddog.domain.order.Order;
import java.util.Optional;

public interface OrderPersist {
    void save(Order order);
    Optional<Order> findBy(String orderUid);
    void delete(Order order);
}
