package ddog.groomer.presentation.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResult {

    private final Boolean isOnboarding;
    private final Boolean isPending;
    private final String email;
    private final String grantType;
    private final String accessToken;

}
