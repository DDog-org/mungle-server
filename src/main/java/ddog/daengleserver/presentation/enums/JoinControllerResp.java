package ddog.daengleserver.presentation.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JoinControllerResp {

    USER_JOIN_COMPLETED("사용자 등록 완료");

    private String message;
}
