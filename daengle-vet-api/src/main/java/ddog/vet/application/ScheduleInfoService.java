package ddog.vet.application;

import ddog.domain.estimate.CareEstimate;
import ddog.domain.estimate.EstimateStatus;
import ddog.domain.estimate.port.CareEstimatePersist;
import ddog.domain.pet.Pet;
import ddog.domain.pet.port.PetPersist;
import ddog.domain.vet.Vet;
import ddog.domain.vet.port.VetPersist;
import ddog.vet.application.exception.account.VetException;
import ddog.vet.application.exception.account.VetExceptionType;
import ddog.vet.presentation.schedule.dto.ScheduleResp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleInfoService {
    private final VetPersist vetPersist;
    private final CareEstimatePersist careEstimatePersist;
    private final PetPersist petPersist;

    public ScheduleResp getScheduleByVetAccountId(Long accountId) {
        Vet savedVet = vetPersist.findByAccountId(accountId).orElseThrow(() -> new VetException(VetExceptionType.VET_NOT_FOUND));

        int estimateTotalCount = careEstimatePersist.findCareEstimatesByVetId(accountId).size();
        int designationCount = careEstimatePersist.findCareEstimatesByVetIdAndProposal(accountId).size();
        int reservationCount = careEstimatePersist.findCareEstimatesByVetIdAndEstimateStatus(accountId).size();

        List<CareEstimate> savedReservations = careEstimatePersist.findTodayCareSchedule(accountId, LocalDate.now(), EstimateStatus.ON_RESERVATION);

        List<ScheduleResp.TodayReservation> toSaveReservation = new ArrayList<>();

        for (CareEstimate reservation : savedReservations) {
            Long petId = reservation.getPetId();
            Pet pet = petPersist.findByPetId(petId).get();
            toSaveReservation.add(ScheduleResp.TodayReservation.builder()
                    .petId(reservation.getPetId())
                    .petName(pet.getName())
                    .petImage(pet.getImageUrl())
                    .estimateId(reservation.getEstimateId())
                    .reservationTime(reservation.getReservedDate().toLocalTime())
                    .desiredStyle(null)
                    .build());
        }


        return ScheduleResp.builder()
                .totalScheduleCount(estimateTotalCount)
                .totalReservationCount(reservationCount)
                .designationCount(designationCount)
                .todayAllReservations(toSaveReservation)
                .build();
    }
}
