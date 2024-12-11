package ddog.persistence.mysql.jpa.repository;

import ddog.domain.payment.enums.ReservationStatus;
import ddog.domain.payment.enums.ServiceType;
import ddog.persistence.mysql.jpa.entity.ReservationJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationJpaRepository extends JpaRepository<ReservationJpaEntity, Long> {

    Optional<ReservationJpaEntity> findByReservationId(Long id);

    Page<ReservationJpaEntity> findByCustomerIdAndServiceTypeAndReservationStatus(
            Long customerId, ServiceType serviceType, ReservationStatus reservationStatus, Pageable pageable);
}
