package ddog.groomer.application;

import ddog.domain.estimate.port.GroomingEstimatePersist;
import ddog.domain.groomer.Groomer;
import ddog.domain.groomer.port.GroomerPersist;
import ddog.domain.payment.Reservation;
import ddog.domain.payment.enums.ServiceType;
import ddog.domain.payment.port.ReservationPersist;
import ddog.domain.pet.Pet;
import ddog.domain.pet.port.PetPersist;

import ddog.groomer.application.exception.GroomingEstimateException;
import ddog.groomer.application.exception.GroomingEstimateExceptionType;
import ddog.groomer.application.exception.account.GroomerException;
import ddog.groomer.application.exception.account.GroomerExceptionType;
import ddog.groomer.application.exception.account.PetException;
import ddog.groomer.application.exception.account.PetExceptionType;
import ddog.groomer.presentation.schedule.dto.ScheduleResp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleInfoService {
    private final GroomerPersist groomerPersist;
    private final PetPersist petPersist;
    private final ReservationPersist reservationPersist;
    private final GroomingEstimatePersist groomingEstimatePersist;

    public ScheduleResp getScheduleByGroomerId(Long accountId) {
        Long savedGroomerId = groomerPersist.findByAccountId(accountId)
                .orElseThrow(() -> new GroomerException(GroomerExceptionType.GROOMER_NOT_FOUND))
                .getGroomerId();

        int estimateTotalCount = groomingEstimatePersist.findGroomingEstimatesByGroomerId(savedGroomerId).size();
        int designationCount = groomingEstimatePersist.findGroomingEstimatesByGroomerIdAndProposal(savedGroomerId).size();
        int reservationCount = groomingEstimatePersist.findGroomingEstimatesByGroomerIdAndEstimateStatus(savedGroomerId).size();

        List<ScheduleResp.TodayReservation> toSaveReservations = reservationPersist
                .findTodayGroomingReservationByPartnerId(LocalDateTime.now(), ServiceType.GROOMING, savedGroomerId)
                .stream()
                .map(reservation -> {
                    Long petId = reservation.getPetId();
                    Long estimateId = reservation.getEstimateId();
                    Pet savedPet = petPersist.findByPetId(petId)
                            .orElseThrow(() -> new PetException(PetExceptionType.PET_NOT_FOUND));
                    String desiredStyle = groomingEstimatePersist.findByEstimateId(estimateId)
                            .orElseThrow(() -> new GroomingEstimateException(GroomingEstimateExceptionType.GROOMING_ESTIMATE_NOT_FOUND))
                            .getDesiredStyle();

                    return ScheduleResp.TodayReservation.builder()
                            .petId(petId)
                            .petName(savedPet.getName())
                            .petImage(savedPet.getImageUrl())
                            .reservationTime(reservation.getSchedule().toLocalTime())
                            .desiredStyle(desiredStyle)
                            .build();
                })
                .toList();

        return ScheduleResp.builder()
                .totalScheduleCount(String.valueOf(estimateTotalCount))
                .totalReservationCount(String.valueOf(reservationCount))
                .designationCount(String.valueOf(designationCount))
                .allReservations(toSaveReservations)
                .build();
    }

}
