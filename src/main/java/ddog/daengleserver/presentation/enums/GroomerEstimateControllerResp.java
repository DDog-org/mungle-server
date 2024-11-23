package ddog.daengleserver.presentation.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GroomerEstimateControllerResp {
    REGISTRATION_COMPLETED("미용사 미용 견적서 등록 완료");
    private String message;

}
