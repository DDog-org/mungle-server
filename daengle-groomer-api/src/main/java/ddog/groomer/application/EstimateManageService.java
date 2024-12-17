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
import ddog.groomer.application.exception.account.*;
import ddog.groomer.presentation.estimate.dto.ReservationEstimateContent;
import ddog.groomer.presentation.estimate.dto.WeekScheduleResp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EstimateManageService {

    private final GroomerPersist groomerPersist;
    private final GroomingEstimatePersist groomingEstimatePersist;
    private final PetPersist petPersist;
    private final UserPersist userPersist;
    private final ReservationPersist reservationPersist;
    public ReservationEstimateContent findEstimateDetailByGroomerIdAndPetId(Long groomerAcoountId, Long reservationId) {
        Long petId = reservationPersist.findByReservationId(reservationId).orElseThrow(()-> new PetException(PetExceptionType.PET_NOT_FOUND)).getPetId();

        GroomingEstimate savedEstimate = groomingEstimatePersist.findEstimateByPetIdAndGroomerAccountId(petId, groomerAcoountId);
        Long userAccountId = savedEstimate.getUserId();

        User savedUser = userPersist.findByAccountId(userAccountId).orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));
        Groomer savedGroomer = groomerPersist.findByAccountId(groomerAcoountId).orElseThrow(() -> new GroomerException(GroomerExceptionType.GROOMER_NOT_FOUND));
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

    public WeekScheduleResp findScheduleByGroomerIdAndDate(Long groomerId, LocalDateTime localDateTime) {
        groomingEstimatePersist.findTodayGroomingSchedule(groomerId, localDateTime.toLocalDate(), EstimateStatus.ON_RESERVATION);

        return null;
    }

}
