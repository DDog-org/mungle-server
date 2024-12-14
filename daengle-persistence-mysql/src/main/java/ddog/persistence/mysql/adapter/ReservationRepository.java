package ddog.persistence.mysql.adapter;

import ddog.domain.payment.Reservation;
import ddog.domain.payment.enums.ReservationStatus;
import ddog.domain.payment.enums.ServiceType;
import ddog.domain.payment.port.ReservationPersist;
import ddog.persistence.mysql.jpa.entity.ReservationJpaEntity;
import ddog.persistence.mysql.jpa.repository.ReservationJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReservationRepository implements ReservationPersist {

    private final ReservationJpaRepository reservationJpaRepository;

    @Override
    public Reservation save(Reservation reservation) {
        return reservationJpaRepository.save(ReservationJpaEntity.from(reservation))
                .toModel();
    }

    @Override
    public Optional<Reservation> findByReservationId(Long reservationId) {
        return reservationJpaRepository.findByReservationId(reservationId).map(ReservationJpaEntity::toModel);
    }

    @Override
    public Page<Reservation> findPaymentHistoryList(Long accountId, ServiceType serviceType, Pageable pageable) {
        return reservationJpaRepository.findByCustomerIdAndServiceTypeAndReservationStatus(
                        accountId, serviceType, ReservationStatus.DEPOSIT_PAID, pageable)
                .map(ReservationJpaEntity::toModel);
    }

    @Override
    public List<Reservation> findByTypeAndStatusAndCustomerId(ServiceType type, ReservationStatus status, Long customerId) {
        return reservationJpaRepository.findReservationJpaEntitiesByServiceTypeAndReservationStatusAndCustomerId(type, status, customerId)
                .stream()
                .map(ReservationJpaEntity::toModel)
                .toList();
    }

    @Override
    public List<Reservation> findTodayCareReservationByPartnerId(LocalDate dateTime, ServiceType serviceType, Long recipientId) {
        return reservationJpaRepository.findTodayReservationByVetId(dateTime, ServiceType.CARE, recipientId)
                .stream().map(ReservationJpaEntity::toModel).toList();
    }

    @Override
    public List<Reservation> findTodayGroomingReservationByPartnerId(LocalDate dateTime, ServiceType serviceType, Long recipientId) {
        return reservationJpaRepository.findTodayReservationByVetId(dateTime, ServiceType.GROOMING, recipientId)
                .stream().map(ReservationJpaEntity::toModel).toList();
    }
}
