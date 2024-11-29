package ddog.auth.dto;

import ddog.account.enums.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResult {

    private final Boolean isOnboarding;
    private final String email;
    private final Role role;
    private final String grantType;
    private final String accessToken;

}
