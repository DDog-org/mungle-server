package ddog.domain.user;

import ddog.domain.pet.Pet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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

}
