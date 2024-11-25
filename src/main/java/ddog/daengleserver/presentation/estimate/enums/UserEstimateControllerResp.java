package ddog.daengleserver.presentation.estimate.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserEstimateControllerResp {
    GENERAL_REGISTRATION_COMPLETED("일반 사용자 견적서 등록 완료"),
    DESIGNATION_REGISTRATION_COMPLETED("지정 미용사 사용자 견적서 등록 완료");

    private String message;

}
