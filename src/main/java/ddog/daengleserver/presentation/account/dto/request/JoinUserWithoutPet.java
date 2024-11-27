package ddog.daengleserver.presentation.account.dto.request;

import ddog.daengleserver.domain.account.enums.Role;
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
