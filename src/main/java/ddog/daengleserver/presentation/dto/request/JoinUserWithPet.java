package ddog.daengleserver.presentation.dto.request;

import ddog.daengleserver.domain.Breed;
import ddog.daengleserver.domain.Gender;
import ddog.daengleserver.domain.Role;
import ddog.daengleserver.domain.Weight;
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
    private Weight petWeight;
    private Breed breed;
}
