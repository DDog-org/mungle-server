package ddog.user.presentation.account.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignUpWithoutPet {
    private String email;

    private String username;
    private String phoneNumber;
    private String nickname;
    private String address;
}
