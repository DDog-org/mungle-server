package ddog.daengleserver.domain.account;

import ddog.daengleserver.presentation.account.dto.request.JoinUserWithPet;
import ddog.daengleserver.presentation.account.dto.request.JoinUserWithoutPet;
import ddog.daengleserver.presentation.account.dto.response.PetInfo;
import ddog.daengleserver.presentation.account.dto.response.UserProfileInfo;
import ddog.daengleserver.presentation.estimate.dto.response.UserInfo;
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
                .image(userImage)
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

    public User withNewPet(Pet newPet) {
        pets.add(newPet);
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

    public PetInfo toPetInfo() {
        List<PetInfo.Detail> petDetails = new ArrayList<>();
        for (Pet pet : pets) {
            petDetails.add(PetInfo.Detail.builder()
                    .id(pet.getPetId())
                    .image(pet.getPetImage())
                    .name(pet.getPetName())
                    .birth(pet.getPetBirth())
                    .gender(pet.getPetGender())
                    .breed(pet.getBreed())
                    .isNeutered(pet.getIsNeutered())
                    .weight(pet.getPetWeight())
                    .groomingExperience(pet.getGroomingExperience())
                    .isBite(pet.getIsBite())
                    .dislikeParts(pet.getDislikeParts().split(","))
                    .significant(pet.getPetSignificant())
                    .build());
        }
        return PetInfo.builder()
                .petDetails(petDetails)
                .build();
    }

    public UserInfo toUserInfo() {
        List<UserInfo.PetInfo> petInfos = new ArrayList<>();
        for (Pet pet : pets) {
            petInfos.add(UserInfo.PetInfo.builder()
                    .id(pet.getPetId())
                    .image(pet.getPetImage())
                    .name(pet.getPetName())
                    .significant(pet.getPetSignificant())
                    .birth(pet.getPetBirth())
                    .weight(pet.getPetWeight())
                    .build());
        }

        return UserInfo.builder()
                .nickname(nickname)
                .userImage(userImage)
                .address(address)
                .petInfos(petInfos)
                .build();
    }
}
