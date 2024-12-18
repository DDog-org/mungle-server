package ddog.vet.application;

import ddog.domain.estimate.CareEstimate;
import ddog.domain.estimate.EstimateStatus;
import ddog.domain.estimate.port.CareEstimatePersist;
import ddog.domain.payment.port.ReservationPersist;
import ddog.domain.pet.Pet;
import ddog.domain.pet.port.PetPersist;
import ddog.domain.user.User;
import ddog.domain.user.port.UserPersist;
import ddog.domain.vet.Vet;
import ddog.domain.vet.port.VetPersist;
import ddog.vet.application.exception.account.*;
import ddog.vet.presentation.estimate.dto.ReservationEstimateContent;
import ddog.vet.presentation.estimate.dto.WeekScheduleResp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EstimateManageService {

    private final VetPersist vetPersist;
    private final CareEstimatePersist careEstimatePersist;
    private final PetPersist petPersist;
    private final UserPersist userPersist;
    private final ReservationPersist reservationPersist;

    public ReservationEstimateContent findEstimateDetailByGroomerIdAndPetId(Long vetAccountId, Long reservationId) {
        Long petId = reservationPersist.findByReservationId(reservationId).orElseThrow(()-> new VetException(VetExceptionType.VET_NOT_FOUND)).getPetId();

        CareEstimate savedEstimate = careEstimatePersist.findEstimateByUserIdAndPetId(vetAccountId, petId).get();
        Long userAccountId = savedEstimate.getUserId();

        User savedUser = userPersist.findByAccountId(userAccountId).orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));
        Vet savedVet = vetPersist.findByAccountId(vetAccountId).orElseThrow(() -> new VetException(VetExceptionType.VET_NOT_FOUND));
        Pet savedPet = petPersist.findByPetId(petId).orElseThrow(() -> new PetException(PetExceptionType.PET_NOT_FOUND));

        return ReservationEstimateContent.builder()
                .userId(userAccountId)
                .userProfile(savedUser.getImageUrl())
                .userName(savedUser.getNickname())
                .partnerAddress(savedVet.getAddress())
                .reservedDate(savedEstimate.getReservedDate())
                .petId(petId)
                .petProfile(savedPet.getImageUrl())
                .petName(savedPet.getName())
                .petAge(savedPet.getAge())
                .petWeight(savedPet.getWeight())
                .dislikeParts(savedPet.getDislikeParts())
                .significantTags(savedPet.getSignificantTags())
                .build();
    }

    public WeekScheduleResp findScheduleByGroomerIdAndDate(Long vetAccountId, String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        System.out.println(localDate);
        List<CareEstimate> CareEstimates = careEstimatePersist.findTodayCareSchedule(vetAccountId, localDate, EstimateStatus.ON_RESERVATION);

        List<WeekScheduleResp.VetSchedule> toSaveSchedule = new ArrayList<>();

        for (CareEstimate careEstimate : CareEstimates) {
            toSaveSchedule.add(
                    WeekScheduleResp.VetSchedule.builder()
                            .scheduleTime(careEstimate.getReservedDate())
                            .reservationId(reservationPersist.findByEstimateId(careEstimate.getEstimateId()).get().getEstimateId())
                            .petId(careEstimate.getPetId())
                            .petName(petPersist.findByPetId(careEstimate.getPetId()).get().getName())
                            .petProfile(petPersist.findByPetId(careEstimate.getPetId()).get().getImageUrl())
                            .build()
            );
        }
        return WeekScheduleResp.builder()
                .scheduleDate(localDate)
                .scheduleList(toSaveSchedule)
                .build();
    }

}
