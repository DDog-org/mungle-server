package ddog.daengleserver.infrastructure.po;

import ddog.daengleserver.domain.Pet;
import ddog.daengleserver.domain.User;
import jakarta.persistence.*;
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
@Entity(name = "Users")
public class UserJpaEntity {

    @Id
    private Long userId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId")
    private AccountJpaEntity account;
    private String username;
    private String phoneNumber;
    private String nickname;
    private String userImage;
    private String address;

    @OneToMany(mappedBy = "userId")
    private List<PetJpaEntity> pets = new ArrayList<>();

    public static UserJpaEntity from(User user, AccountJpaEntity account) {
        return UserJpaEntity.builder()
                .account(account)
                .username(user.getUsername())
                .phoneNumber(user.getPhoneNumber())
                .nickname(user.getNickname())
                .userImage(user.getUserImage())
                .address(user.getAddress())
                .pets(UserJpaEntity.fromPetModel(user.getPets()))
                .build();
    }

    public User toModel() {
        return User.builder()
                .accountId(account.getAccountId())
                .username(username)
                .phoneNumber(phoneNumber)
                .nickname(nickname)
                .userImage(userImage)
                .address(address)
                .pets(toPetModel())
                .build();
    }

    private List<Pet> toPetModel() {
        List<Pet> petModel = new ArrayList<>();
        for (PetJpaEntity pet : pets) {
            petModel.add(pet.toModel());
        }
        return petModel;
    }

    private static List<PetJpaEntity> fromPetModel(List<Pet> petModel) {
        List<PetJpaEntity> pets = new ArrayList<>();
        for (Pet pet : petModel) {
            pets.add(PetJpaEntity.from(pet));
        }
        return pets;
    }
}
