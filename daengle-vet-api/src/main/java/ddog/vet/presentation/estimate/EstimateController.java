package ddog.vet.presentation.estimate;

import ddog.auth.dto.PayloadDto;
import ddog.vet.presentation.estimate.dto.CareEstimateReq;
import ddog.vet.presentation.estimate.dto.CareEstimateInfo;
import ddog.vet.presentation.estimate.dto.CareEstimateDetail;
import ddog.vet.application.EstimateService;
import ddog.vet.application.exception.common.CommonResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static ddog.vet.presentation.estimate.EstimateControllerResp.REGISTRATION_COMPLETED;
import static ddog.vet.application.exception.common.CommonResponseEntity.success;

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
    public CommonResponseEntity<CareEstimateDetail> getCareEstimateDetail(@PathVariable Long careEstimateId) {
        return success(estimateService.getCareEstimateDetailInfo(careEstimateId));
    }

    @PostMapping
    public CommonResponseEntity<String> createCareEstimate(@RequestBody CareEstimateReq request, PayloadDto payloadDto) {
        estimateService.createVetCareEstimate(request, payloadDto.getAccountId());
        return success(REGISTRATION_COMPLETED.getMessage());
    }
}
