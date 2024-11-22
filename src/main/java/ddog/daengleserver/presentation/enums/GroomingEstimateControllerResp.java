package ddog.daengleserver.presentation.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GroomingEstimateControllerResp {
    REGISTRATION_COMPLETED("사용자 미용 견적서 등록 완료");

    private String message;

}
