package ddog.user.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ValidResponse {

    @Builder
    @Getter
    public static class Nickname{
        private Boolean isAvailable;
    }
}
