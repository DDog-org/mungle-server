package ddog.pet;

import ddog.user.dto.request.AddPetInfo;
import ddog.user.dto.request.JoinUserWithPet;
import ddog.user.dto.request.ModifyPetInfo;
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
    private Long accountId;
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
                .accountId(accountId)
                .petName(request.getPetName())
                .petGender(request.getPetGender())
                .petWeight(request.getPetWeight())
                .petBirth(request.getPetBirth())
                .isNeutered(request.getIsNeutered())
                .breed(request.getBreed())
                .build();
    }

    public static Pet create(Long accountId, AddPetInfo request) {
        return Pet.builder()
                .accountId(accountId)
                .petGender(request.getGender())
                .petName(request.getName())
                .petImage(request.getImage())
                .petSignificant(request.getSignificant())
                .petBirth(request.getBirth())
                .petWeight(request.getWeight())
                .breed(request.getBreed())
                .isNeutered(request.getIsNeutered())
                .groomingExperience(request.getGroomingExperience())
                .isBite(request.getIsBite())
                .dislikeParts(request.getDislikeParts())
                .build();
    }

    public static Pet withModifyPetInfo(ModifyPetInfo request, Long accountId) {
        return Pet.builder()
                .petId(request.getId())
                .accountId(accountId)
                .petImage(request.getImage())
                .petName(request.getName())
                .petBirth(request.getBirth())
                .petGender(request.getGender())
                .breed(request.getBreed())
                .isNeutered(request.getIsNeutered())
                .petWeight(request.getWeight())
                .groomingExperience(request.getGroomingExperience())
                .isBite(request.getIsBite())
                .dislikeParts(request.getDislikeParts())
                .petSignificant(request.getSignificant())
                .build();
    }
}