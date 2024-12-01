package ddog.user.presentation.account.dto;

import ddog.domain.account.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignUpWithoutPet {
    private String email;
    private Role role;

    private String username;
    private String phoneNumber;
    private String nickname;
    private String address;
}
