package ddog.daengleserver.domain.account;

import ddog.daengleserver.domain.account.enums.Breed;
import ddog.daengleserver.domain.account.enums.Gender;
import ddog.daengleserver.domain.account.enums.Weight;
import ddog.daengleserver.presentation.account.dto.request.AddPetInfoReq;
import ddog.daengleserver.presentation.account.dto.request.JoinUserWithPet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pet {

    private Long petId;
    private Long userId;
    private Gender petGender;
    private String petName;
    private String petImage;
    private String petSignificant;
    private int petBirth;
    private Weight petWeight;
    private Breed breed;
    private Boolean isNeutered;
    private Boolean groomingExperience;
    private Boolean isBite;
    private String dislikeParts;

    public static Pet toJoinPetInfo(Long accountId, JoinUserWithPet request) {
        return Pet.builder()
                .userId(accountId)
                .petName(request.getPetName())
                .petGender(request.getPetGender())
                .petBirth(request.getPetBirth())
                .petBirth(request.getPetBirth())
                .breed(request.getBreed())
                .build();
    }

    public static Pet create(Long accountId, AddPetInfoReq request) {
        return Pet.builder()
                .userId(accountId)
                .petGender(request.getPetGender())
                .petName(request.getPetName())
                .petImage(request.getPetImage())
                .petSignificant(request.getPetSignificant())
                .petBirth(request.getPetBirth())
                .petWeight(request.getPetWeight())
                .breed(request.getBreed())
                .isNeutered(request.getIsNeutered())
                .groomingExperience(request.getGroomingExperience())
                .isBite(request.getIsBite())
                .dislikeParts(request.getDislikeParts())
                .build();
    }
}