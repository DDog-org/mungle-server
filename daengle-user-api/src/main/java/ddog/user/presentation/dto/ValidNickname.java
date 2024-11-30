package ddog.user.presentation.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ValidNickname {

    private Boolean isAvailable;

}
