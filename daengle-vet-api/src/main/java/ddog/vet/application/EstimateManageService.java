package ddog.vet.application;

import ddog.domain.estimate.CareEstimate;
import ddog.domain.estimate.EstimateStatus;
import ddog.domain.estimate.port.CareEstimatePersist;
import ddog.domain.payment.enums.ServiceType;
import ddog.domain.payment.port.ReservationPersist;
import ddog.domain.pet.Pet;
import ddog.domain.pet.port.PetPersist;
import ddog.domain.user.User;
import ddog.domain.user.port.UserPersist;
import ddog.domain.vet.Vet;
import ddog.domain.vet.port.VetPersist;
import ddog.vet.application.exception.CareEstimateException;
import ddog.vet.application.exception.CareEstimateExceptionType;
import ddog.vet.application.exception.account.*;
import ddog.vet.presentation.estimate.dto.PetInfo;
import ddog.vet.presentation.estimate.dto.ReservationEstimateContent;
import ddog.vet.presentation.estimate.dto.WeekScheduleResp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public ReservationEstimateContent findEstimateByVetAccountIdAndReservationId(Long vetAccountId, Long reservationId) {
        Long petId = reservationPersist.findByReservationId(reservationId).orElseThrow(()-> new PetException(PetExceptionType.PET_NOT_FOUND)).getPetId();
        Long estimateId = reservationPersist.findByReservationId(reservationId).orElseThrow().getEstimateId();

        CareEstimate savedEstimate = careEstimatePersist.findByEstimateId(estimateId).orElseThrow(() -> new CareEstimateException(CareEstimateExceptionType.CARE_ESTIMATE_NOT_FOUND));
        Long userAccountId = savedEstimate.getUserId();

        User savedUser = userPersist.findByAccountId(userAccountId).orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));
        Vet savedVet = vetPersist.findByAccountId(vetAccountId).orElseThrow(() -> new VetException(VetExceptionType.VET_NOT_FOUND));
        Pet savedPet = petPersist.findByPetId(petId).orElseThrow(() -> new PetException(PetExceptionType.PET_NOT_FOUND));
        ReservationEstimateContent.toVetPetInfo petEstimateInfo = ReservationEstimateContent.toVetPetInfo.builder()
                .symptoms(savedEstimate.getSymptoms())
                .requirements(savedEstimate.getRequirements())
                .treatment(savedEstimate.getTreatment())
                .cause(savedEstimate.getCause())
                .diagnosis(savedEstimate.getDiagnosis())
                .build();

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
                .petInfo(petEstimateInfo)
                .build();
    }

    public WeekScheduleResp findScheduleByVetIdAndDate(Long vetAccountId, String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);

        LocalDateTime startOfDay = localDate.atStartOfDay();
        LocalDateTime endOfDay = localDate.plusDays(1).atStartOfDay();

        List<CareEstimate> CareEstimates = careEstimatePersist.findTodayCareSchedule(vetAccountId, startOfDay, endOfDay, EstimateStatus.ON_RESERVATION);

        List<WeekScheduleResp.VetSchedule> toSaveSchedule = new ArrayList<>();

        for (CareEstimate careEstimate : CareEstimates) {
            toSaveSchedule.add(
                    WeekScheduleResp.VetSchedule.builder()
                            .scheduleTime(careEstimate.getReservedDate().toLocalTime())
                            .reservationId(reservationPersist.findByEstimateIdAndType(careEstimate.getEstimateId(), ServiceType.CARE).orElseThrow().getReservationId())
                            .petId(careEstimate.getPetId())
                            .petName(petPersist.findByPetId(careEstimate.getPetId()).orElseThrow().getName())
                            .petProfile(petPersist.findByPetId(careEstimate.getPetId()).orElseThrow().getImageUrl())
                            .build()
            );
        }
        return WeekScheduleResp.builder()
                .scheduleDate(date)
                .scheduleList(toSaveSchedule)
                .build();
    }

    public PetInfo findPetInfoByPetId(Long petId){
        Pet pet = petPersist.findByPetId(petId).orElseThrow(()-> new PetException(PetExceptionType.PET_NOT_FOUND));

        return PetInfo.builder()
                .petId(petId)
                .image(pet.getImageUrl())
                .name(pet.getName())
                .birth(pet.getBirth())
                .gender(pet.getGender())
                .breed(pet.getBreed())
                .isNeutered(pet.getIsNeutered())
                .weight(pet.getWeight())
                .groomingExperience(pet.getGroomingExperience())
                .isBite(pet.getIsBite())
                .dislikeParts(pet.getDislikeParts())
                .significantTags(pet.getSignificantTags())
                .significant(pet.getSignificant())
                .build();

    }

}
