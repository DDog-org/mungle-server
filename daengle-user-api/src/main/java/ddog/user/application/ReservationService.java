package ddog.user.application;

import ddog.domain.estimate.CareEstimate;
import ddog.domain.estimate.GroomingEstimate;
import ddog.domain.estimate.port.CareEstimatePersist;
import ddog.domain.estimate.port.GroomingEstimatePersist;
import ddog.domain.groomer.Groomer;
import ddog.domain.groomer.port.GroomerPersist;
import ddog.domain.payment.Reservation;
import ddog.domain.payment.enums.ReservationStatus;
import ddog.domain.payment.enums.ServiceType;
import ddog.domain.payment.port.ReservationPersist;
import ddog.domain.pet.Pet;
import ddog.domain.pet.port.PetPersist;
import ddog.domain.vet.Vet;
import ddog.domain.vet.port.VetPersist;
import ddog.user.application.exception.account.*;
import ddog.user.application.exception.estimate.*;
import ddog.user.application.mapper.ReservationMapper;
import ddog.user.presentation.reservation.dto.EstimateDetail;
import ddog.user.presentation.reservation.dto.ReservationInfo;
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
    private final PetPersist petPersist;
    private final VetPersist vetPersist;
    private final GroomerPersist groomerPersist;
    private final GroomingEstimatePersist groomingEstimatePersist;
    private final CareEstimatePersist careEstimatePersist;

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

    public ReservationInfo.Grooming findGroomingEstimates(Long accountId) {
        List<Reservation> estimates = reservationPersist.findByTypeAndStatusAndCustomerId(ServiceType.GROOMING, ReservationStatus.DEPOSIT_PAID, accountId);

        List<ReservationInfo.Grooming.Content> contents = new ArrayList<>();
        for (Reservation estimate : estimates) {
            Pet pet = petPersist.findByPetId(estimate.getPetId())
                    .orElseThrow(() -> new PetException(PetExceptionType.PET_NOT_FOUND));

            contents.add(ReservationMapper.mapToGroomingContent(estimate, pet));
        }

        return ReservationInfo.Grooming.builder()
                .contents(contents)
                .build();
    }

    public ReservationInfo.Care findCareEstimates(Long accountId) {
        List<Reservation> estimates = reservationPersist.findByTypeAndStatusAndCustomerId(ServiceType.CARE, ReservationStatus.DEPOSIT_PAID, accountId);

        List<ReservationInfo.Care.Content> contents = new ArrayList<>();
        for (Reservation estimate : estimates) {
            Pet pet = petPersist.findByPetId(estimate.getPetId())
                    .orElseThrow(() -> new PetException(PetExceptionType.PET_NOT_FOUND));

            contents.add(ReservationMapper.mapToCareContent(estimate, pet));
        }

        return ReservationInfo.Care.builder()
                .contents(contents)
                .build();
    }

    public EstimateDetail.Grooming getGroomingEstimateDetail(Long estimateId) {
        GroomingEstimate estimate = groomingEstimatePersist.findByEstimateId(estimateId)
                .orElseThrow(() -> new GroomingEstimateException(GroomingEstimateExceptionType.GROOMING_ESTIMATE_NOT_FOUND));

        Groomer groomer = groomerPersist.findByGroomerId(estimate.getGroomerId())
                .orElseThrow(() -> new GroomerException(GroomerExceptionType.GROOMER_NOT_FOUND));

        Reservation reservation = reservationPersist.findByEstimateId(estimateId)
                .orElseThrow(() -> new ReservationException(ReservationExceptionType.RESERVATION_NOT_FOUND));

        Pet pet = petPersist.findByPetId(estimate.getPetId())
                .orElseThrow(() -> new PetException(PetExceptionType.PET_NOT_FOUND));

        return ReservationMapper.mapToGroomingEstimateDetail(reservation.getReservationId(), estimateId, groomer, estimate, pet);
    }

    public EstimateDetail.Care getCareEstimateDetail(Long estimateId) {
        CareEstimate estimate = careEstimatePersist.findByEstimateId(estimateId)
                .orElseThrow(() -> new CareEstimateException(CareEstimateExceptionType.CARE_ESTIMATE_NOT_FOUND));

        Vet vet = vetPersist.findByVetId(estimate.getVetId())
                .orElseThrow(() -> new VetException(VetExceptionType.VET_NOT_FOUND));

        Reservation reservation = reservationPersist.findByEstimateId(estimateId)
                .orElseThrow(() -> new ReservationException(ReservationExceptionType.RESERVATION_NOT_FOUND));

        return ReservationMapper.mapToCareEstimateDetail(reservation.getReservationId(), estimateId, vet, estimate);
    }
}
