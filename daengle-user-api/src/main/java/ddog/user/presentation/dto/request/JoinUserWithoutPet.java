package ddog.user.presentation.dto.request;

import ddog.domain.account.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JoinUserWithoutPet {
    private String email;
    private Role role;

    private String username;
    private String phoneNumber;
    private String nickname;
    private String address;
}
