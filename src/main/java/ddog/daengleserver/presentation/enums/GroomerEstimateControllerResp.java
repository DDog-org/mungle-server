package ddog.daengleserver.presentation.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GroomerEstimateControllerResp {
    GENERAL_REGISTRATION_COMPLETED("일반 미용사 미용 견적서 등록 완료"),
    DESIGNATION_REGISTRATION_COMPLETED("지정 미용사 미용 견적서 등록 완료");

    private String message;

}
