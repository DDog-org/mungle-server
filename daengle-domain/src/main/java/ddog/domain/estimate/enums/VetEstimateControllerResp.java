package ddog.domain.estimate.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VetEstimateControllerResp {
    REGISTRATION_COMPLETED("수의사 진료 견적서 등록 완료");
    private String message;

}
