package ddog.groomer.presentation;

import ddog.auth.dto.PayloadDto;
import ddog.domain.estimate.dto.request.GroomerGroomingEstimateReq;
import ddog.domain.estimate.dto.response.GroomingEstimateInfo;
import ddog.domain.estimate.dto.response.UserGroomingEstimateDetail;
import ddog.groomer.application.EstimateService;
import ddog.groomer.application.exception.common.CommonResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static ddog.domain.estimate.enums.GroomerEstimateControllerResp.REGISTRATION_COMPLETED;
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
    public CommonResponseEntity<UserGroomingEstimateDetail> getGroomingEstimateDetail(@PathVariable Long groomingEstimateId) {
        return success(estimateService.getGroomingEstimateDetailInfo(groomingEstimateId));
    }

    @PostMapping
    public CommonResponseEntity<String> createGroomingEstimate(@RequestBody GroomerGroomingEstimateReq request, PayloadDto payloadDto) {
        estimateService.createGroomerGroomingEstimate(request, payloadDto.getAccountId());
//        sseEmitterService.sendMessageToUser(payloadDto.getAccountId(), REGISTRATION_COMPLETED.getMessage());
        return success(REGISTRATION_COMPLETED.getMessage());
    }
}
