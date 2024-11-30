package ddog.domain.user.dto.request;

import ddog.domain.account.Role;
import ddog.domain.pet.Breed;
import ddog.domain.pet.Gender;
import ddog.domain.pet.Weight;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JoinUserWithPet {
    private String email;
    private Role role;

    private String username;
    private String phoneNumber;
    private String nickname;
    private String address;
    private String petName;
    private int petBirth;
    private Gender petGender;
    private Boolean isNeutered;
    private Weight petWeight;
    private Breed breed;
}
