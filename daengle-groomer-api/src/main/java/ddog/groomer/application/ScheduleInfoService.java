package ddog.groomer.application;

import ddog.domain.estimate.port.GroomingEstimatePersist;
import ddog.domain.groomer.port.GroomerPersist;
import ddog.domain.payment.Reservation;
import ddog.domain.payment.enums.ServiceType;
import ddog.domain.payment.port.ReservationPersist;
import ddog.domain.pet.Pet;
import ddog.domain.pet.port.PetPersist;

import ddog.groomer.application.exception.account.GroomerException;
import ddog.groomer.application.exception.account.GroomerExceptionType;
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

    public ScheduleResp getScheduleByGroomerAccountId(Long accountId) {
        Long savedGroomerId = groomerPersist.findByAccountId(accountId)
                .orElseThrow(() -> new GroomerException(GroomerExceptionType.GROOMER_NOT_FOUND))
                .getGroomerId();

        int estimateTotalCount = groomingEstimatePersist.findGroomingEstimatesByGroomerId(savedGroomerId).size();
        int designationCount = groomingEstimatePersist.findGroomingEstimatesByGroomerIdAndProposal(savedGroomerId).size();
        int reservationCount = groomingEstimatePersist.findGroomingEstimatesByGroomerIdAndEstimateStatus(savedGroomerId).size();
        System.out.println(estimateTotalCount+ " + " +designationCount+ " + " + reservationCount);

        List<Reservation> savedReservation = reservationPersist.findTodayGroomingReservationByPartnerId(LocalDateTime.now(), ServiceType.GROOMING, savedGroomerId);
        List<ScheduleResp.TodayReservation> toSaveReservations = new ArrayList<>();

        for (Reservation reservation : savedReservation) {
            Long petId = reservation.getPetId();
            Pet pet = petPersist.findByPetId(petId).get();
            toSaveReservations.add(ScheduleResp.TodayReservation.builder()
                    .petId(petId)
                    .petName(pet.getName())
                    .reservationTime(reservation.getSchedule().toLocalTime())
                    .desiredStyle(null)
                    .build());

        }

        return ScheduleResp.builder()
                .totalScheduleCount(String.valueOf(estimateTotalCount))
                .totalReservationCount(String.valueOf(reservationCount))
                .designationCount(String.valueOf(designationCount))
                .allReservations(toSaveReservations)
                .build();
    }

}
