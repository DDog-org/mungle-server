package ddog.groomer.application;

import ddog.domain.estimate.EstimateStatus;
import ddog.domain.estimate.GroomingEstimate;
import ddog.domain.estimate.port.GroomingEstimatePersist;
import ddog.domain.groomer.Groomer;
import ddog.domain.groomer.port.GroomerPersist;
import ddog.domain.payment.port.ReservationPersist;
import ddog.domain.pet.Pet;
import ddog.domain.pet.port.PetPersist;

import ddog.groomer.application.exception.account.GroomerException;
import ddog.groomer.application.exception.account.GroomerExceptionType;
import ddog.groomer.presentation.schedule.dto.ScheduleResp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

        int estimateTotalCount = groomingEstimatePersist.findGroomingEstimatesByGroomerId(accountId).size();
        int designationCount = groomingEstimatePersist.findGroomingEstimatesByGroomerIdAndProposal(accountId).size();
        int reservationCount = groomingEstimatePersist.findGroomingEstimatesByGroomerIdAndEstimateStatus(accountId).size();

        Groomer savedGroomer = groomerPersist.findByAccountId(accountId).orElseThrow(()-> new GroomerException(GroomerExceptionType.GROOMER_NOT_FOUND));

        List<GroomingEstimate> savedReservation = groomingEstimatePersist.findTodayGroomingSchedule(accountId, LocalDate.now(), EstimateStatus.ON_RESERVATION);
        List<ScheduleResp.TodayReservation> toSaveReservations = new ArrayList<>();

        for (GroomingEstimate reservation : savedReservation) {
            Long petId = reservation.getPetId();
            Pet pet = petPersist.findByPetId(petId).get();
            GroomingEstimate savedGroomingEstimate =groomingEstimatePersist.findByEstimateId(reservation.getEstimateId()).get();
            toSaveReservations.add(ScheduleResp.TodayReservation.builder()
                    .petId(petId)
                    .petName(pet.getName())
                    .reservationTime(reservation.getReservedDate().toLocalTime())
                    .desiredStyle(savedGroomingEstimate.getDesiredStyle())
                    .estimateId(savedGroomingEstimate.getEstimateId())
                    .build());

        }

        return ScheduleResp.builder()
                .totalScheduleCount(String.valueOf(estimateTotalCount))
                .totalReservationCount(String.valueOf(reservationCount))
                .designationCount(String.valueOf(designationCount))
                .todayAllReservations(toSaveReservations)
                .build();
    }

}
