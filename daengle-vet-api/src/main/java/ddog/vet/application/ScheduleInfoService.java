package ddog.vet.application;

import ddog.domain.estimate.port.CareEstimatePersist;
import ddog.domain.payment.Reservation;
import ddog.domain.payment.enums.ServiceType;
import ddog.domain.payment.port.ReservationPersist;
import ddog.domain.pet.Pet;
import ddog.domain.pet.port.PetPersist;
import ddog.domain.vet.Vet;
import ddog.domain.vet.port.VetPersist;
import ddog.vet.application.exception.account.VetException;
import ddog.vet.application.exception.account.VetExceptionType;
import ddog.vet.presentation.schedule.dto.ScheduleResp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleInfoService {
    private final VetPersist vetPersist;
    private final CareEstimatePersist careEstimatePersist;
    private final PetPersist petPersist;
    private final ReservationPersist reservationPersist;

    public ScheduleResp getScheduleByVetId(Long vetId) {
        Vet vetInfo = vetPersist.findByVetId(vetId).orElseThrow(() -> new VetException(VetExceptionType.VET_NOT_FOUND));

        int estimateTotalCount = careEstimatePersist.findCareEstimatesByVetId(vetId).size();
        int designationCount = careEstimatePersist.findCareEstimatesByVetIdAndProposal(vetId).size();
        int reservationCount = careEstimatePersist.findCareEstimatesByVetIdAndEstimateStatus(vetId).size();

        List<Reservation> findReservations = reservationPersist.findTodayCareReservationByPartnerId(LocalDateTime.now(), ServiceType.CARE, vetId);

        List<ScheduleResp.TodayReservation> todayReservation = new ArrayList<>();

        for (Reservation reservation : findReservations) {
            Long petId = reservation.getPetId();
            Pet pet = petPersist.findByPetId(petId).get();
            todayReservation.add(ScheduleResp.TodayReservation.builder()
                    .petId(reservation.getPetId())
                    .petName(pet.getName())
                    .petImage(pet.getImageUrl())
                    .reservationTime(reservation.getSchedule().toLocalTime())
                    .desiredStyle(null)
                    .build());
        }


        return ScheduleResp.builder()
                .totalScheduleCount(String.valueOf(estimateTotalCount))
                .totalReservationCount(String.valueOf(reservationCount))
                .designationCount(String.valueOf(designationCount))
                .allReservations(todayReservation)
                .build();
    }
}
