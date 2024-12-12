package ddog.domain.payment.port;

import ddog.domain.payment.Reservation;
import ddog.domain.payment.enums.ReservationStatus;
import ddog.domain.payment.enums.ServiceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationPersist {
    Reservation save(Reservation reservation);

    Optional<Reservation> findByReservationId(Long reservationId);

    Page<Reservation> findPaymentHistoryList(Long accountId, ServiceType serviceType, Pageable pageable);

    List<Reservation> findByTypeAndStatusAndCustomerId(ServiceType serviceType, ReservationStatus status, Long accountId);

    List<Reservation> findTodayCareReservationByPartnerId(LocalDateTime dateTime, ServiceType serviceType, Long recipientId);

    List<Reservation> findTodayGroomingReservationByPartnerId(LocalDateTime dateTime, ServiceType serviceType, Long recipientId);

}
