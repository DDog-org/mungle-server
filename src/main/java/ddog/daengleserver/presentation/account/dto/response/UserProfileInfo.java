package ddog.daengleserver.presentation.account.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileInfo {

    private String userImage;
    private String nickname;
    private String username;
    private String phoneNumber;
    private String email;

}
