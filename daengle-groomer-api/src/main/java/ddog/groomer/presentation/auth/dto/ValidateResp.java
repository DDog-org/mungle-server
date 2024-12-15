package ddog.groomer.presentation.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ValidateResp {
    private boolean IsValidateMember;
}
