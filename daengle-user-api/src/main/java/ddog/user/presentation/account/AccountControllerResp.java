package ddog.user.presentation.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountControllerResp {

    USER_JOIN_COMPLETED("사용자 등록 완료"),
    PROFILE_MODIFY_COMPLETED("사용자 프로필 수정 완료"),
    PET_ADD_COMPLETED("반려견 등록 완료"),
    PET_PROFILE_MODIFY_COMPLETED("반려견 프로필 수정 완료"),
    DELETE_PET_COMPLETED("반려견 프로필 삭제 완료");

    private String message;
}
