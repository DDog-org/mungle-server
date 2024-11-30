package ddog.daengleserver.presentation.estimate;

import ddog.daengleserver.application.CareEstimateService;
import ddog.daengleserver.global.auth.dto.PayloadDto;
import ddog.daengleserver.global.common.CommonResponseEntity;
import ddog.daengleserver.presentation.estimate.dto.request.VetCareEstimateReq;
import ddog.daengleserver.presentation.estimate.dto.response.CareEstimateInfo;
import ddog.daengleserver.presentation.estimate.dto.response.UserCareEstimateDetail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static ddog.daengleserver.global.common.CommonResponseEntity.success;
import static ddog.daengleserver.presentation.estimate.enums.VetEstimateControllerResp.REGISTRATION_COMPLETED;

@Tag(name = "수의사 진료 견적서 관련 API")
@RestController
@RequestMapping("/api/vet/estimate")
@RequiredArgsConstructor
public class VetEstimateController {

    private final CareEstimateService careEstimateService;

    @Operation(summary = "사용자가 요청한 진료 견적서 목록 조회", description = "같은 주소에 위치한 사용자가 요청한 진료 견적서 목록 조회 (신규)")
    @GetMapping("/list")
    public CommonResponseEntity<CareEstimateInfo> findCareEstimateInfo(PayloadDto payloadDto) {
        return success(careEstimateService.findCareEstimateInfo(payloadDto.getAccountId()));
    }

    @Operation(summary = "진료 견적서 상세 내용 조회")
    @GetMapping("/{careEstimateId}/detail")
    public CommonResponseEntity<UserCareEstimateDetail> getCareEstimateDetail(@PathVariable Long careEstimateId) {
        return success(careEstimateService.getCareEstimateDetailInfo(careEstimateId));
    }

    @Operation(summary = "사용자에게 진료 견적서 요청", description = "진료 견적서를 요청한 사용자에게 종합 소견을 덧붙여 견적서 전송 (신규 -> 대기)")
    @PostMapping
    public CommonResponseEntity<String> createCareEstimate(@RequestBody VetCareEstimateReq request, PayloadDto payloadDto) {
        careEstimateService.createVetCareEstimate(request, payloadDto.getAccountId());
        return success(REGISTRATION_COMPLETED.getMessage());
    }
}
