package ddog.domain.payment.port;

import ddog.domain.payment.Reservation;
import ddog.domain.payment.enums.ServiceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ReservationPersist {
    Reservation save(Reservation reservation);
    Optional<Reservation> findByReservationId(Long reservationId);

    Page<Reservation> findPaymentHistoryList(Long accountId, ServiceType serviceType, Pageable pageable);
}
