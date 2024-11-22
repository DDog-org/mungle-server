package ddog.daengleserver.domain;

import ddog.daengleserver.presentation.dto.response.UserAddressAndPetsInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    private String username;
    private String userImage;
    private String address;
    private List<Pet> pets;

    public UserAddressAndPetsInfo getAddressAndPetsInfo() {

        List<UserAddressAndPetsInfo.PetInfo> petInfos = new ArrayList<>();
        for (Pet pet : pets) {
            petInfos.add(UserAddressAndPetsInfo.PetInfo.builder()
                    .petId(pet.getPetId())
                    .petImage(pet.getPetImage())
                    .petName(pet.getPetName())
                    .build());
        }

        return UserAddressAndPetsInfo.builder()
                .address(this.address)
                .petInfos(petInfos)
                .build();
    }
}
