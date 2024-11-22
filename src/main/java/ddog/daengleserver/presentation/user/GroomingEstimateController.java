package ddog.daengleserver.presentation.user;

import ddog.daengleserver.application.GroomingEstimateService;
import ddog.daengleserver.global.common.CommonResponseEntity;
import ddog.daengleserver.presentation.dto.request.DesignationGroomingEstimateReq;
import ddog.daengleserver.presentation.dto.request.GeneralGroomingEstimateReq;
import ddog.daengleserver.presentation.dto.response.UserAddressAndPetsInfo;
import ddog.daengleserver.presentation.enums.GroomingEstimateControllerResp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static ddog.daengleserver.global.common.CommonResponseEntity.success;

@Tag(name = "사용자 미용 견적 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/daengle")
public class GroomingEstimateController {

    private final GroomingEstimateService groomingEstimateService;

    @Operation(summary = "사용자 주소, 반려동물 정보 요청", description = "사용자 주소와 반려동물 id, 사진, 이름 정보를 가져옵니다.")
    @GetMapping("address-pets-info/{userId}")
    public CommonResponseEntity<UserAddressAndPetsInfo> getAddressAndPetsInfo(@PathVariable Long userId) {
        return success(groomingEstimateService.getUserAddressAndPetsInfoById(userId));
    }

    @Operation(summary = "미용 견적서 작성 (일반)", description = "일반 미용 견적서 작성")
    @PostMapping("general-estimate")
    public CommonResponseEntity<String> createGeneralGroomingEstimate(@RequestBody GeneralGroomingEstimateReq generalGroomingEstimateReq) {
        groomingEstimateService.createGeneralGroomingEstimate(generalGroomingEstimateReq);
        return success(GroomingEstimateControllerResp.GENERAL_REGISTRATION_COMPLETED.getMessage());
    }

    @Operation(summary = "미용 견적서 작성 (지정)", description = "미용사 지정 견적서 작성")
    @PostMapping("designation-estimate")
    public CommonResponseEntity<String> createDesignationGroomingEstimate(@RequestBody DesignationGroomingEstimateReq designationGroomingEstimateReq) {
        groomingEstimateService.createDesignationGroomingEstimate(designationGroomingEstimateReq);
        return success(GroomingEstimateControllerResp.DESIGNATION_REGISTRATION_COMPLETED.getMessage());
    }
}
