package ddog.daengleserver.domain;

import ddog.daengleserver.presentation.dto.request.JoinUserWithPet;
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


}