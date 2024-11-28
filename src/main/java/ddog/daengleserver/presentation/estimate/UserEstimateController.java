package ddog.daengleserver.presentation.estimate;

import ddog.daengleserver.application.CareEstimateService;
import ddog.daengleserver.application.GroomingEstimateService;
import ddog.daengleserver.application.UserService;
import ddog.daengleserver.global.auth.dto.PayloadDto;
import ddog.daengleserver.global.common.CommonResponseEntity;
import ddog.daengleserver.presentation.estimate.dto.request.UserDesignationCareEstimateReq;
import ddog.daengleserver.presentation.estimate.dto.request.UserDesignationGroomingEstimateReq;
import ddog.daengleserver.presentation.estimate.dto.request.UserGeneralCareEstimateReq;
import ddog.daengleserver.presentation.estimate.dto.request.UserGeneralGroomingEstimateReq;
import ddog.daengleserver.presentation.estimate.dto.response.CareEstimateDetails;
import ddog.daengleserver.presentation.estimate.dto.response.EstimateInfo;
import ddog.daengleserver.presentation.estimate.dto.response.GroomingEstimateDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static ddog.daengleserver.global.common.CommonResponseEntity.success;
import static ddog.daengleserver.presentation.estimate.enums.UserEstimateControllerResp.*;

@Tag(name = "사용자 견적서 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/daengle/estimate")
public class UserEstimateController {

    private final UserService userService;
    private final CareEstimateService careEstimateService;
    private final GroomingEstimateService groomingEstimateService;

    @Operation(summary = "미용 견적서 작성 (일반)", description = "일반 미용 견적서 작성")
    @PostMapping("/general-grooming")
    public CommonResponseEntity<String> createGeneralGroomingEstimate(@RequestBody UserGeneralGroomingEstimateReq request, PayloadDto payloadDto) {
        groomingEstimateService.createUserGeneralGroomingEstimate(request, payloadDto.getAccountId());
        return success(GENERAL_GROOMING_REGISTRATION.getMessage());
    }

    @Operation(summary = "미용 견적서 작성 (지정)", description = "미용사 지정 미용 견적서 작성")
    @PostMapping("/designation-grooming")
    public CommonResponseEntity<String> createDesignationGroomingEstimate(@RequestBody UserDesignationGroomingEstimateReq request, PayloadDto payloadDto) {
        groomingEstimateService.createUserDesignationGroomingEstimate(request, payloadDto.getAccountId());
        return success(DESIGNATION_GROOMING_REGISTRATION.getMessage());
    }

    @Operation(summary = "진료 견적서 작성 (일반)", description = "일반 진료 견적서 작성")
    @PostMapping("/general-care")
    public CommonResponseEntity<String> createGeneralCareEstimate(@RequestBody UserGeneralCareEstimateReq request, PayloadDto payloadDto) {
        careEstimateService.createUserGeneralCareEstimate(request, payloadDto.getAccountId());
        return success(GENERAL_CARE_REGISTRATION.getMessage());
    }

    @Operation(summary = "진료 견적서 작성 (지정)", description = "수의사 지정 진료 견적서 작성")
    @PostMapping("/designation-care")
    public CommonResponseEntity<String> createDesignationCareEstimate(@RequestBody UserDesignationCareEstimateReq request, PayloadDto payloadDto) {
        careEstimateService.createUserDesignationCareEstimate(request, payloadDto.getAccountId());
        return success(DESIGNATION_CARE_REGISTRATION.getMessage());
    }

    @Operation(summary = "미용/진료 대기 견적서 목록 조회")
    @GetMapping("/list")
    public CommonResponseEntity<EstimateInfo> findEstimateInfo(PayloadDto payloadDto) {
        return success(userService.findEstimateInfo(payloadDto.getAccountId()));
    }

    @GetMapping("/{groomingEstimateId}/grooming-detail")
    public CommonResponseEntity<GroomingEstimateDetails> getGroomingEstimateDetails(@PathVariable Long groomingEstimateId) {
        return success(userService.getGroomingEstimateDetails(groomingEstimateId));
    }

    @GetMapping("/{careEstimateId}/care-detail")
    public CommonResponseEntity<CareEstimateDetails> getCareEstimateDetails(@PathVariable Long careEstimateId) {
        return success(userService.getCareEstimateDetails(careEstimateId));
    }
}
