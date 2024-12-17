package ddog.domain.user;

import ddog.domain.groomer.enums.GroomingBadge;
import ddog.domain.pet.Pet;
import ddog.domain.pet.SignificantTag;
import ddog.domain.pet.Weight;
import ddog.domain.vet.enums.CareBadge;
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

    public int calculateDaengleMeterWithGroomingBadge(int daengleMeter, List<GroomingBadge> badges) {
        if (pets.isEmpty() || badges.isEmpty()) {
            return daengleMeter;
        }

        int ADDITIONAL_METER = 3;
        int finalDaengleMeter = daengleMeter;

        Pet pet = pets.get(0);
        for (GroomingBadge badge : badges) {
            if (badge.equals(GroomingBadge.DANGEROUS_DOG)) {
                if (pet.getIsBite()) {
                    finalDaengleMeter += ADDITIONAL_METER;
                }
            } else if (badge.equals(GroomingBadge.LARGE_DOG)) {
                if (pet.getWeight().equals(Weight.LARGE)) {
                    finalDaengleMeter += ADDITIONAL_METER;
                }
            } else if (badge.equals(GroomingBadge.OLD_DOG)) {
                if (pet.getAge() >= 10) {
                    finalDaengleMeter += ADDITIONAL_METER;
                }
            } else if (badge.equals(GroomingBadge.SKIN_DISEASE)) {
                List<SignificantTag> significantTags = pet.getSignificantTags();
                if (significantTags.isEmpty()) {
                    continue;
                }
                if (significantTags.contains(SignificantTag.SKIN_DISEASES)) {
                    finalDaengleMeter += ADDITIONAL_METER;
                }
            }
        }

        return finalDaengleMeter;
    }

    public int calculateDaengleMeterWithCareBadge(int daengleMeter, List<CareBadge> badges) {
        if (pets.isEmpty() || badges.isEmpty()) {
            return daengleMeter;
        }

        int ADDITIONAL_METER = 3;
        int finalDaengleMeter = daengleMeter;

        Pet pet = pets.get(0);
        for (CareBadge badge : badges) {
            if (badge.equals(CareBadge.DANGEROUS_DOG)) {
                if (pet.getIsBite()) {
                    finalDaengleMeter += ADDITIONAL_METER;
                }
            } else if (badge.equals(CareBadge.LARGE_DOG)) {
                if (pet.getWeight().equals(Weight.LARGE)) {
                    finalDaengleMeter += ADDITIONAL_METER;
                }
            } else if (badge.equals(CareBadge.OLD_DOG)) {
                if (pet.getAge() >= 10) {
                    finalDaengleMeter += ADDITIONAL_METER;
                }
            } else if (badge.equals(CareBadge.SKIN_DISEASE)) {
                List<SignificantTag> significantTags = pet.getSignificantTags();
                if (significantTags.isEmpty()) {
                    continue;
                }
                if (significantTags.contains(SignificantTag.SKIN_DISEASES)) {
                    finalDaengleMeter += ADDITIONAL_METER;
                }
            } else if (badge.equals(CareBadge.HEART_DISEASE)) {
                List<SignificantTag> significantTags = pet.getSignificantTags();
                if (significantTags.isEmpty()) {
                    continue;
                }
                if (significantTags.contains(SignificantTag.HEART_DISEASE)) {
                    finalDaengleMeter += ADDITIONAL_METER;
                }
            }
        }

        return finalDaengleMeter;
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
