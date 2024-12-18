package ddog.groomer.application;

import ddog.domain.estimate.EstimateStatus;
import ddog.domain.estimate.GroomingEstimate;
import ddog.domain.estimate.port.GroomingEstimatePersist;
import ddog.domain.groomer.Groomer;
import ddog.domain.groomer.port.GroomerPersist;
import ddog.domain.payment.port.ReservationPersist;
import ddog.domain.pet.Pet;
import ddog.domain.pet.port.PetPersist;
import ddog.domain.user.User;
import ddog.domain.user.port.UserPersist;
import ddog.groomer.application.exception.ReservationException;
import ddog.groomer.application.exception.ReservationExceptionType;
import ddog.groomer.application.exception.account.*;
import ddog.groomer.presentation.estimate.dto.ReservationEstimateContent;
import ddog.groomer.presentation.estimate.dto.WeekScheduleResp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EstimateManageService {

    private final GroomerPersist groomerPersist;
    private final GroomingEstimatePersist groomingEstimatePersist;
    private final PetPersist petPersist;
    private final UserPersist userPersist;
    private final ReservationPersist reservationPersist;

    public ReservationEstimateContent findEstimateDetailByGroomerIdAndPetId(Long groomerAccountId, Long reservationId) {
        Long petId = reservationPersist.findByReservationId(reservationId).orElseThrow(()-> new PetException(PetExceptionType.PET_NOT_FOUND)).getPetId();

        GroomingEstimate savedEstimate = groomingEstimatePersist.findEstimateByPetIdAndGroomerAccountId(petId, groomerAccountId);
        Long userAccountId = savedEstimate.getUserId();

        User savedUser = userPersist.findByAccountId(userAccountId).orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));
        Groomer savedGroomer = groomerPersist.findByAccountId(groomerAccountId).orElseThrow(() -> new GroomerException(GroomerExceptionType.GROOMER_NOT_FOUND));
        Pet savedPet = petPersist.findByPetId(petId).orElseThrow(() -> new PetException(PetExceptionType.PET_NOT_FOUND));

        return ReservationEstimateContent.builder()
                .userId(userAccountId)
                .userProfile(savedUser.getImageUrl())
                .userName(savedUser.getNickname())
                .partnerAddress(savedGroomer.getAddress())
                .reservedDate(savedEstimate.getReservedDate())
                .petId(petId)
                .petProfile(savedPet.getImageUrl())
                .petName(savedPet.getName())
                .petAge(savedPet.getAge())
                .petWeight(savedPet.getWeight())
                .dislikeParts(savedPet.getDislikeParts())
                .significantTags(savedPet.getSignificantTags())
                .desiredStyle(savedEstimate.getDesiredStyle())
                .requirements(savedEstimate.getRequirements())
                .overallOpinion(savedEstimate.getOverallOpinion())
                .build();
    }

    public WeekScheduleResp findScheduleByGroomerIdAndDate(Long groomerAccountId, String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);

        List<GroomingEstimate> groomingEstimates = groomingEstimatePersist.findTodayGroomingSchedule(groomerAccountId, localDate, EstimateStatus.ON_RESERVATION);

        List<WeekScheduleResp.GroomerSchedule> toSaveSchedule = new ArrayList<>();

        for (GroomingEstimate groomingEstimate :groomingEstimates) {
            toSaveSchedule.add(
                    WeekScheduleResp.GroomerSchedule.builder()
                            .scheduleTime(groomingEstimate.getReservedDate())
                            .reservationId(reservationPersist.findByEstimateId(groomingEstimate.getEstimateId()).orElseThrow(()-> new ReservationException(ReservationExceptionType.RESERVATION_NOT_FOUND)).getReservationId())
                            .petId(groomingEstimate.getPetId())
                            .petName(petPersist.findByPetId(groomingEstimate.getPetId()).get().getName())
                            .petProfile(petPersist.findByPetId(groomingEstimate.getPetId()).get().getImageUrl())
                            .desiredStyle(groomingEstimate.getDesiredStyle())
                            .build()
            );
        }

        return WeekScheduleResp.builder()
                .scheduleDate(localDate)
                .scheduleList(toSaveSchedule)
                .build();
    }

}
