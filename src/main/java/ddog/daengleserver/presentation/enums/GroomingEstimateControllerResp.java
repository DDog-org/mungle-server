package ddog.daengleserver.presentation.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GroomingEstimateControllerResp {
    GENERAL_REGISTRATION_COMPLETED("일반 사용자 미용 견적서 등록 완료"),
    DESIGNATION_REGISTRATION_COMPLETED("지정 사용자 미용 견적서 등록 완료");

    private String message;

}
