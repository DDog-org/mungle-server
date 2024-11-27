package ddog.daengleserver.domain;

import ddog.daengleserver.presentation.account.dto.request.JoinUserWithPet;
import ddog.daengleserver.presentation.account.dto.request.JoinUserWithoutPet;
import ddog.daengleserver.presentation.account.dto.response.UserProfileInfo;
import ddog.daengleserver.presentation.estimate.dto.response.UserAndPetsInfo;
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
    private Long accountId;
    private String username;
    private String phoneNumber;
    private String nickname;
    private String userImage;
    private String address;
    private String email;
    private List<Pet> pets;

    public static User createWithoutPet(Long accountId, JoinUserWithoutPet request) {
        return User.builder()
                .accountId(accountId)
                .username(request.getUsername())
                .phoneNumber(request.getPhoneNumber())
                .nickname(request.getNickname())
                .address(request.getAddress())
                .email(request.getEmail())
                .pets(new ArrayList<>())
                .build();
    }

    public static User createWithPet(Long accountId, JoinUserWithPet request, Pet pet) {
        List<Pet> pets = new ArrayList<>();
        pets.add(pet);
        return User.builder()
                .accountId(accountId)
                .username(request.getUsername())
                .phoneNumber(request.getPhoneNumber())
                .nickname(request.getNickname())
                .address(request.getAddress())
                .email(request.getEmail())
                .pets(pets)
                .build();
    }

    public UserProfileInfo toUserProfileInfo() {
        return UserProfileInfo.builder()
                .userImage(userImage)
                .nickname(nickname)
                .username(username)
                .phoneNumber(phoneNumber)
                .email(email)
                .build();
    }

    public User withImageAndNickname(String userImage, String nickname) {
        return User.builder()
                .userId(userId)
                .accountId(accountId)
                .username(username)
                .phoneNumber(phoneNumber)
                .nickname(nickname)
                .userImage(userImage)
                .address(address)
                .email(email)
                .pets(pets)
                .build();
    }

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
