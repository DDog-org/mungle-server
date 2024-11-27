package ddog.daengleserver.domain;

import ddog.daengleserver.presentation.estimate.dto.response.UserAndPetsInfo;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long userId;
    private Long accountId;
    private String username;
    private String nickname;
    private String userImage;
    private String address;
    private List<Pet> pets;

    public UserAndPetsInfo findAddressAndPetsInfo() {

        List<UserAndPetsInfo.PetInfo> petInfos = new ArrayList<>();
        for (Pet pet : pets) {
            petInfos.add(UserAndPetsInfo.PetInfo.builder()
                    .petId(pet.getPetId())
                    .petImage(pet.getPetImage())
                    .petName(pet.getPetName())
                    .petSignificant(pet.getPetSignificant())
                    .petBirth(pet.getPetBirth())
                    .petWeight(pet.getPetWeight())
                    .build());
        }

        return UserAndPetsInfo.builder()
                .nickname(nickname)
                .userImage(userImage)
                .address(address)
                .petInfos(petInfos)
                .build();
    }
}
