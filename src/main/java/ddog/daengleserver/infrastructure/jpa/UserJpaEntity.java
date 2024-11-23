package ddog.daengleserver.infrastructure.jpa;

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
    private String userImage;
    private String nickname;
    private String address;

    @OneToMany(mappedBy = "userId")
    private List<PetJpaEntity> pets = new ArrayList<>();

    public static UserJpaEntity from(User user) {
        return UserJpaEntity.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .userImage(user.getUserImage())
                .address(user.getAddress())
                .build();
    }

    public User toModel() {
        return User.builder()
                .userId(this.userId)
                .username(this.username)
                .userImage(this.userImage)
                .nickname(this.nickname)
                .address(this.address)
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
}
