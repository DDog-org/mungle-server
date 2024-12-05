package ddog.persistence.mysql.jpa.repository;


import ddog.persistence.mysql.jpa.entity.PaymentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository extends JpaRepository<PaymentJpaEntity, Long> {
}
