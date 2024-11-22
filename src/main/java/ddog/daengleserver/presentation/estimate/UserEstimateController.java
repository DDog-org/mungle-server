package ddog.daengleserver.presentation.estimate;

import ddog.daengleserver.application.GroomingEstimateService;
import ddog.daengleserver.global.auth.dto.PayloadDto;
import ddog.daengleserver.global.common.CommonResponseEntity;
import ddog.daengleserver.presentation.estimate.dto.request.UserDesignationGroomingEstimateReq;
import ddog.daengleserver.presentation.estimate.dto.request.UserGeneralGroomingEstimateReq;
import ddog.daengleserver.presentation.estimate.dto.response.UserAndPetsInfo;
import ddog.daengleserver.presentation.estimate.enums.GroomingEstimateControllerResp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static ddog.daengleserver.global.common.CommonResponseEntity.success;

@Tag(name = "사용자 견적서 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/daengle/estimate")
public class UserEstimateController {

    private final GroomingEstimateService groomingEstimateService;

    @Operation(summary = "사용자 주소, 반려동물 정보 요청", description = "사용자 주소와 반려동물 id, 사진, 이름 정보를 가져옵니다.")
    @GetMapping("/user-pets-info")
    public CommonResponseEntity<UserAndPetsInfo> getAddressAndPetsInfo(PayloadDto payloadDto) {
        return success(groomingEstimateService.getUserAddressAndPetsInfoById(payloadDto.getAccountId()));
    }

    @Operation(summary = "미용 견적서 작성 (일반)", description = "일반 미용 견적서 작성")
    @PostMapping("/general-grooming")
    public CommonResponseEntity<String> createGeneralGroomingEstimate(@RequestBody UserGeneralGroomingEstimateReq userGeneralGroomingEstimateReq) {
        groomingEstimateService.createUserGeneralGroomingEstimate(userGeneralGroomingEstimateReq);
        return success(GroomingEstimateControllerResp.GENERAL_REGISTRATION_COMPLETED.getMessage());
    }

    @Operation(summary = "미용 견적서 작성 (지정)", description = "미용사 지정 견적서 작성")
    @PostMapping("/designation-grooming")
    public CommonResponseEntity<String> createDesignationGroomingEstimate(@RequestBody UserDesignationGroomingEstimateReq userDesignationGroomingEstimateReq) {
        groomingEstimateService.createUserDesignationGroomingEstimate(userDesignationGroomingEstimateReq);
        return success(GroomingEstimateControllerResp.DESIGNATION_REGISTRATION_COMPLETED.getMessage());
    }
}
