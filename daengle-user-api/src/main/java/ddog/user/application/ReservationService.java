package ddog.user.application;

import ddog.domain.groomer.port.GroomerPersist;
import ddog.domain.payment.Reservation;
import ddog.domain.payment.enums.ReservationStatus;
import ddog.domain.payment.enums.ServiceType;
import ddog.domain.payment.port.ReservationPersist;
import ddog.domain.pet.Pet;
import ddog.domain.pet.port.PetPersist;
import ddog.domain.user.port.UserPersist;
import ddog.user.application.exception.account.PetException;
import ddog.user.application.exception.account.PetExceptionType;
import ddog.user.application.exception.estimate.ReservationException;
import ddog.user.application.exception.estimate.ReservationExceptionType;
import ddog.user.presentation.reservation.dto.EstimateInfo;
import ddog.user.presentation.reservation.dto.ReservationSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationPersist reservationPersist;
    private final UserPersist userPersist;
    private final GroomerPersist groomerPersist;
    private final PetPersist petPersist;

    @Transactional(readOnly = true)
    public ReservationSummary getReservationSummary(Long reservationId) {
        Reservation reservation = reservationPersist.findByReservationId(reservationId).orElseThrow(()
                -> new ReservationException(ReservationExceptionType.RESERVATION_NOT_FOUND));

        return ReservationSummary.builder()
                .reservationId(reservation.getReservationId())
                .recipientName(reservation.getRecipientName())
                .shopName(reservation.getShopName())
                .schedule(reservation.getSchedule())
                .build();
    }

    public EstimateInfo.Grooming findGroomingEstimates(Long accountId) {
        List<Reservation> estimates = reservationPersist.findByTypeAndStatusAndCustomerId(ServiceType.GROOMING, ReservationStatus.DEPOSIT_PAID, accountId);

        List<EstimateInfo.Grooming.Content> contents = new ArrayList<>();
        for (Reservation estimate : estimates) {
            Pet pet = petPersist.findByPetId(estimate.getPetId())
                    .orElseThrow(() -> new PetException(PetExceptionType.PET_NOT_FOUND));

            contents.add(EstimateInfo.Grooming.Content.builder()
                    .estimateId(estimate.getEstimateId())
                    .groomerName(estimate.getRecipientName())
                    .petName(pet.getName())
                    .petImageUrl(pet.getImageUrl())
                    .shopName(estimate.getShopName())
                    .reservedDate(estimate.getSchedule())
                    .build());
        }

        return EstimateInfo.Grooming.builder()
                .contents(contents)
                .build();
    }

    public EstimateInfo.Care findCareEstimates(Long accountId) {
        List<Reservation> estimates = reservationPersist.findByTypeAndStatusAndCustomerId(ServiceType.CARE, ReservationStatus.DEPOSIT_PAID, accountId);

        List<EstimateInfo.Care.Content> contents = new ArrayList<>();
        for (Reservation estimate : estimates) {
            Pet pet = petPersist.findByPetId(estimate.getPetId())
                    .orElseThrow(() -> new PetException(PetExceptionType.PET_NOT_FOUND));

            contents.add(EstimateInfo.Care.Content.builder()
                    .estimateId(estimate.getEstimateId())
                    .vetName(estimate.getRecipientName())
                    .petName(pet.getName())
                    .petImageUrl(pet.getImageUrl())
                    .reservedDate(estimate.getSchedule())
                    .build());
        }

        return EstimateInfo.Care.builder()
                .contents(contents)
                .build();
    }
}
