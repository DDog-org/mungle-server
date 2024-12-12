package ddog.user.application.mapper;

import ddog.domain.pet.Part;
import ddog.domain.pet.Pet;
import ddog.domain.pet.SignificantTag;
import ddog.domain.user.User;
import ddog.user.presentation.account.dto.PetInfo;
import ddog.user.presentation.account.dto.ProfileInfo;
import ddog.user.presentation.account.dto.SignUpWithPet;
import ddog.user.presentation.account.dto.SignUpWithoutPet;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static User createWithPet(Long accountId, SignUpWithPet request, Pet pet) {
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

    public static User createWithoutPet(Long accountId, SignUpWithoutPet request) {
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

    public static ProfileInfo.UpdatePage toUserProfileInfo(User user) {
        return ProfileInfo.UpdatePage.builder()
                .image(user.getImageURL())
                .nickname(user.getNickname())
                .username(user.getUsername())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .build();
    }

    public static PetInfo toPetInfo(User user) {
        List<PetInfo.Detail> petDetails = new ArrayList<>();
        for (Pet pet : user.getPets()) {

            List<PetInfo.Detail.PartDetail> dislikeParts = new ArrayList<>();
            for (Part part : pet.getDislikeParts()) {
                dislikeParts.add(PetInfo.Detail.PartDetail.builder()
                        .partName(part.getName())
                        .part(part.toString())
                        .build());
            }

            List<PetInfo.Detail.TagDetail> significantTags = new ArrayList<>();
            for (SignificantTag tag : pet.getSignificantTags()) {
                significantTags.add(PetInfo.Detail.TagDetail.builder()
                        .tagName(tag.getName())
                        .tag(tag.toString())
                        .build());
            }

            petDetails.add(PetInfo.Detail.builder()
                    .id(pet.getPetId())
                    .image(pet.getImageURL())
                    .name(pet.getName())
                    .birth(pet.getBirth())
                    .gender(pet.getGender())
                    .breed(pet.getBreed())
                    .isNeutered(pet.getIsNeutered())
                    .weight(pet.getWeight())
                    .groomingExperience(pet.getGroomingExperience())
                    .isBite(pet.getIsBite())
                    .dislikeParts(dislikeParts)
                    .significantTags(significantTags)
                    .significant(pet.getSignificant())
                    .build());
        }
        return PetInfo.builder()
                .petDetails(petDetails)
                .build();
    }
}
