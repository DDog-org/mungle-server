package ddog.vet.presentation.estimate;

import ddog.auth.dto.PayloadDto;
import ddog.auth.exception.common.CommonResponseEntity;
import ddog.vet.presentation.estimate.dto.EstimateReq;
import ddog.vet.presentation.estimate.dto.EstimateInfo;
import ddog.vet.presentation.estimate.dto.EstimateDetail;
import ddog.vet.application.EstimateService;
import ddog.vet.presentation.estimate.dto.EstimateResp;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static ddog.auth.exception.common.CommonResponseEntity.success;

@RestController
@RequestMapping("/api/vet/estimate")
@RequiredArgsConstructor
public class EstimateController {

    private final EstimateService estimateService;

    @GetMapping("/list")
    public CommonResponseEntity<EstimateInfo> findEstimateInfo(PayloadDto payloadDto) {
        return success(estimateService.findEstimateInfo(payloadDto.getAccountId()));
    }

    @GetMapping("/{careEstimateId}/detail")
    public CommonResponseEntity<EstimateDetail> getEstimateDetail(@PathVariable Long careEstimateId) {
        return success(estimateService.getEstimateDetail(careEstimateId));
    }

    @PostMapping
    public CommonResponseEntity<EstimateResp> createEstimate(@RequestBody EstimateReq request, PayloadDto payloadDto) {
        return success(estimateService.createEstimate(request, payloadDto.getAccountId()));
    }
}
