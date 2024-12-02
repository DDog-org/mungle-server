package ddog.user.presentation.account.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProfileInfo {

    @Getter
    @Builder
    public static class ModifyPage {
        private String image;
        private String nickname;
        private String username;
        private String phoneNumber;
        private String email;
    }

}
