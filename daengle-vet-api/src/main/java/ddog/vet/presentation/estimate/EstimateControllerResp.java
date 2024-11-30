package ddog.vet.presentation.estimate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EstimateControllerResp {
    REGISTRATION_COMPLETED("수의사 진료 견적서 등록 완료");
    private String message;

}
