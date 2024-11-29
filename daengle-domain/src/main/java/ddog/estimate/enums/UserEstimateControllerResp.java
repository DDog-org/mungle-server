package ddog.estimate.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserEstimateControllerResp {
    GENERAL_GROOMING_REGISTRATION("일반 사용자 미용 견적서 등록 완료"),
    DESIGNATION_GROOMING_REGISTRATION("지정 미용사 사용자 미용 견적서 등록 완료"),
    GENERAL_CARE_REGISTRATION("일반 사용자 진료 견적서 등록 완료"),
    DESIGNATION_CARE_REGISTRATION("지정 수의사 사용자 진료 견적서 등록 완료");

    private String message;

}
