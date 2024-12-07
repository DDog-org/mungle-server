package ddog.vet.presentation.estimate;

import ddog.auth.dto.PayloadDto;
import ddog.auth.exception.common.CommonResponseEntity;
import ddog.vet.application.EstimateService;
import ddog.vet.presentation.estimate.dto.CreatePendingEstimateReq;
import ddog.vet.presentation.estimate.dto.EstimateDetail;
import ddog.vet.presentation.estimate.dto.EstimateInfo;
import ddog.vet.presentation.estimate.dto.EstimateResp;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static ddog.auth.exception.common.CommonResponseEntity.success;

@RestController
@RequestMapping("/api/vet/estimate")
@RequiredArgsConstructor
public class EstimateController {

    private final EstimateService estimateService;

    /* (신규) 진료 견적서들 목록 조회 */
    @GetMapping("/list")
    public CommonResponseEntity<EstimateInfo> findEstimates(PayloadDto payloadDto) {
        return success(estimateService.findEstimates(payloadDto.getAccountId()));
    }

    /* (신규) 진료 견적서 상세 조회 */
    @GetMapping("/{estimateId}/detail")
    public CommonResponseEntity<EstimateDetail> getEstimateDetail(@PathVariable Long estimateId) {
        return success(estimateService.getEstimateDetail(estimateId));
    }

    /* 병원 -> 사용자 (대기) 진료 견적서 작성 */
    @PostMapping
    public CommonResponseEntity<EstimateResp> createEstimate(@RequestBody CreatePendingEstimateReq request, PayloadDto payloadDto) {
        return success(estimateService.createPendingEstimate(request, payloadDto.getAccountId()));
    }
}
