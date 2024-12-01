package ddog.groomer.presentation.account.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignUpResp {
    private boolean isRegistered;
}
