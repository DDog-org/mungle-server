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
    private String imageUrl;
    private String address;
    private String email;
    private List<Pet> pets;

    public static void validateUsername(String username) {
        if (username == null || username.length() < 2 || username.length() > 10) {
            throw new IllegalArgumentException("Invalid username: must be 2-10 characters.");
        }
    }

    public static void validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || !phoneNumber.matches("^010-\\d{4}-\\d{4}$")) {
            throw new IllegalArgumentException("Invalid phone number: must follow format 010-0000-0000.");
        }
    }

    public static void validateNickname(String nickname) {
        if (nickname == null || nickname.length() < 2 || nickname.length() > 10) {
            throw new IllegalArgumentException("Invalid nickname: must be 2-10 characters.");
        }
    }

    public static void validateAddress(String address) {
        /* TODO 공공데이터 추가 후 재작업
        if (address == null || !address.matches("^\\S+시 \\S+구 \\S+동$")) {
            throw new IllegalArgumentException("Invalid address: must follow format '00시 00구 00동'.");
        }*/
    }

    public User withImageAndNickname(String imageUrl, String nickname) {
        return User.builder()
                .userId(userId)
                .accountId(accountId)
                .username(username)
                .phoneNumber(phoneNumber)
                .nickname(nickname)
                .imageUrl(imageUrl)
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
                .imageUrl(imageUrl)
                .address(address)
                .email(email)
                .pets(pets)
                .build();
    }
}
