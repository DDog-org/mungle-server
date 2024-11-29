package ddog.daengleserver.presentation.account.dto.request;

import ddog.account.enums.Breed;
import ddog.account.enums.Gender;
import ddog.account.enums.Role;
import ddog.account.enums.Weight;
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
