package ddog.daengleserver.presentation.account.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserAccountControllerResp {

    USER_JOIN_COMPLETED("사용자 등록 완료"),
    PROFILE_MODIFY_COMPLETED("사용자 프로필 수정 완료"),;

    private String message;
}
