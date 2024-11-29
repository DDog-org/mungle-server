package ddog.vet.presentation;

import ddog.auth.dto.PayloadDto;
import ddog.domain.estimate.dto.request.VetCareEstimateReq;
import ddog.domain.estimate.dto.response.CareEstimateInfo;
import ddog.domain.estimate.dto.response.UserCareEstimateDetail;
import ddog.vet.application.EstimateService;
import ddog.vet.exception.common.CommonResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static ddog.domain.estimate.enums.VetEstimateControllerResp.REGISTRATION_COMPLETED;
import static ddog.vet.exception.common.CommonResponseEntity.success;

@RestController
@RequestMapping("/api/vet/estimate")
@RequiredArgsConstructor
public class EstimateController {

    private final EstimateService estimateService;

    @GetMapping("/list")
    public CommonResponseEntity<CareEstimateInfo> findCareEstimateInfo(PayloadDto payloadDto) {
        return success(estimateService.findCareEstimateInfo(payloadDto.getAccountId()));
    }

    @GetMapping("/{careEstimateId}/detail")
    public CommonResponseEntity<UserCareEstimateDetail> getCareEstimateDetail(@PathVariable Long careEstimateId) {
        return success(estimateService.getCareEstimateDetailInfo(careEstimateId));
    }

    @PostMapping
    public CommonResponseEntity<String> createCareEstimate(@RequestBody VetCareEstimateReq request, PayloadDto payloadDto) {
        estimateService.createVetCareEstimate(request, payloadDto.getAccountId());
        return success(REGISTRATION_COMPLETED.getMessage());
    }
}
