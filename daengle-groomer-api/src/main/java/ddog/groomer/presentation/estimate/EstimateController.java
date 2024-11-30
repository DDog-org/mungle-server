package ddog.groomer.presentation.estimate;

import ddog.auth.dto.PayloadDto;
import ddog.domain.estimate.dto.request.GroomerGroomingEstimateReq;
import ddog.groomer.presentation.estimate.dto.GroomingEstimateInfo;
import ddog.groomer.presentation.estimate.dto.GroomingEstimateDetail;
import ddog.groomer.application.EstimateService;
import ddog.groomer.application.exception.common.CommonResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static ddog.groomer.presentation.estimate.EstimateControllerResp.REGISTRATION_COMPLETED;
import static ddog.groomer.application.exception.common.CommonResponseEntity.success;

@RestController
@RequestMapping("/api/groomer/estimate")
@RequiredArgsConstructor
public class EstimateController {

    private final EstimateService estimateService;
//    private final SseEmitterService sseEmitterService;

    @GetMapping("/list")
    public CommonResponseEntity<GroomingEstimateInfo> findGroomingEstimateInfo(PayloadDto payloadDto) {
        return success(estimateService.findGroomingEstimateInfo(payloadDto.getAccountId()));
    }

    @GetMapping("/{groomingEstimateId}/detail")
    public CommonResponseEntity<GroomingEstimateDetail> getGroomingEstimateDetail(@PathVariable Long groomingEstimateId) {
        return success(estimateService.getGroomingEstimateDetailInfo(groomingEstimateId));
    }

    @PostMapping
    public CommonResponseEntity<String> createGroomingEstimate(@RequestBody GroomerGroomingEstimateReq request, PayloadDto payloadDto) {
        estimateService.createGroomerGroomingEstimate(request, payloadDto.getAccountId());
//        sseEmitterService.sendMessageToUser(payloadDto.getAccountId(), REGISTRATION_COMPLETED.getMessage());
        return success(REGISTRATION_COMPLETED.getMessage());
    }
}
