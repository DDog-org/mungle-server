package ddog.user.application.mapper;

import ddog.domain.pet.Pet;
import ddog.user.presentation.account.dto.AddPetInfo;
import ddog.user.presentation.account.dto.SignUpWithPet;
import ddog.user.presentation.account.dto.UpdatePetInfo;

public class PetMapper {

    public static Pet toJoinPetInfo(Long accountId, SignUpWithPet request) {
        return Pet.builder()
                .accountId(accountId)
                .name(request.getPetName())
                .gender(request.getPetGender())
                .imageUrl("")
                .weight(request.getPetWeight())
                .birth(request.getPetBirth())
                .isNeutered(request.getIsNeutered())
                .breed(request.getBreed())
                .build();
    }

    public static Pet create(Long accountId, AddPetInfo request) {
        return Pet.builder()
                .accountId(accountId)
                .gender(request.getGender())
                .name(request.getName())
                .imageUrl(request.getImage())
                .significant(request.getSignificant())
                .birth(request.getBirth())
                .weight(request.getWeight())
                .breed(request.getBreed())
                .isNeutered(request.getIsNeutered())
                .groomingExperience(request.getGroomingExperience())
                .isBite(request.getIsBite())
                .dislikeParts(request.getDislikeParts())
                .significantTags(request.getSignificantTags())
                .build();
    }

    public static Pet withModifyPetInfo(UpdatePetInfo request, Long accountId) {
        return Pet.builder()
                .petId(request.getId())
                .accountId(accountId)
                .imageUrl(request.getImage())
                .name(request.getName())
                .birth(request.getBirth())
                .gender(request.getGender())
                .breed(request.getBreed())
                .isNeutered(request.getIsNeutered())
                .weight(request.getWeight())
                .groomingExperience(request.getGroomingExperience())
                .isBite(request.getIsBite())
                .dislikeParts(request.getDislikeParts())
                .significantTags(request.getSignificantTags())
                .significant(request.getSignificant())
                .build();
    }

}
