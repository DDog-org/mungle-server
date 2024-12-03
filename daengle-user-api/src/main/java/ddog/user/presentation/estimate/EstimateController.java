package ddog.user.presentation.estimate;

import ddog.auth.dto.PayloadDto;
import ddog.user.application.EstimateService;
import ddog.user.application.exception.common.CommonResponseEntity;
import ddog.user.presentation.estimate.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import static ddog.user.application.exception.common.CommonResponseEntity.success;
import static ddog.user.presentation.estimate.EstimateControllerResp.CARE_ESTIMATE_REGISTRATION;
import static ddog.user.presentation.estimate.EstimateControllerResp.GROOMING_ESTIMATE_REGISTRATION;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/estimate")
public class EstimateController {

    private final EstimateService estimateService;

    @PostMapping("/groomer-user-info")
    public CommonResponseEntity<AccountInfo.Grooming> getGroomerAndUserInfo(@RequestBody GroomerInfoReq request, PayloadDto payloadDto) {
        return success(estimateService.getGroomerAndUserInfo(request.getGroomerId(), payloadDto.getAccountId()));
    }

    @PostMapping("/vet-user-info")
    public CommonResponseEntity<AccountInfo.Care> getVetAndUserInfo(@RequestBody VetInfoReq request, PayloadDto payloadDto) {
        return success(estimateService.getVetAndUserInfo(request.getVetId(), payloadDto.getAccountId()));
    }

    @PostMapping("/grooming")
    public CommonResponseEntity<String> createGroomingEstimate(@RequestBody GroomingEstimateReq request, PayloadDto payloadDto) {
        estimateService.createGroomingEstimate(request, payloadDto.getAccountId());
        return success(GROOMING_ESTIMATE_REGISTRATION.getMessage());
    }

    @PostMapping("/care")
    public CommonResponseEntity<String> createCareEstimate(@RequestBody CareEstimateReq request, PayloadDto payloadDto) {
        estimateService.createCareEstimate(request, payloadDto.getAccountId());
        return success(CARE_ESTIMATE_REGISTRATION.getMessage());
    }

    @GetMapping("/list")
    public CommonResponseEntity<EstimateInfo> findEstimateInfo(PayloadDto payloadDto) {
        return success(estimateService.findEstimateInfo(payloadDto.getAccountId()));
    }

    @GetMapping("/{groomingEstimateId}/detail")
    public CommonResponseEntity<GroomingEstimateDetail> getGroomingEstimateDetail(@PathVariable Long groomingEstimateId) {
        return success(estimateService.getGroomingEstimateDetail(groomingEstimateId));
    }

    @GetMapping("/{careEstimateId}/detail")
    public CommonResponseEntity<CareEstimateDetail> getCareEstimateDetail(@PathVariable Long careEstimateId) {
        return success(estimateService.getCareEstimateDetail(careEstimateId));
    }
}
