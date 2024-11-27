package ddog.daengleserver.presentation.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountControllerResp {
    UPDATE_COMPLETED("업데이트 완료");

    private String message;
}
