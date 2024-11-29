package ddog.groomer.presentation;

import ddog.daengleserver.application.GroomingEstimateService;
import ddog.daengleserver.global.auth.dto.PayloadDto;
import ddog.daengleserver.global.common.CommonResponseEntity;
import ddog.daengleserver.global.infra.sse.SseEmitterService;
import ddog.daengleserver.presentation.estimate.dto.request.GroomerGroomingEstimateReq;
import ddog.daengleserver.presentation.estimate.dto.response.UserGroomingEstimateDetail;
import ddog.daengleserver.presentation.estimate.dto.response.GroomingEstimateInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static ddog.daengleserver.global.common.CommonResponseEntity.success;
import static ddog.daengleserver.presentation.estimate.enums.GroomerEstimateControllerResp.REGISTRATION_COMPLETED;

@Tag(name = "미용사 미용 견적서 관련 API")
@RestController
@RequestMapping("/api/groomer/estimate")
@RequiredArgsConstructor
public class GroomerEstimateController {

    private final GroomingEstimateService groomingEstimateService;
    private final SseEmitterService sseEmitterService;

    @Operation(summary = "사용자가 요청한 미용 신규 견적서 목록 조회")
    @GetMapping("/list")
    public CommonResponseEntity<GroomingEstimateInfo> findGroomingEstimateInfo(PayloadDto payloadDto) {
        return success(groomingEstimateService.findGroomingEstimateInfo(payloadDto.getAccountId()));
    }

    @Operation(summary = "미용 견적서 상세 내용 조회")
    @GetMapping("/{groomingEstimateId}/detail")
    public CommonResponseEntity<UserGroomingEstimateDetail> getGroomingEstimateDetail(@PathVariable Long groomingEstimateId) {
        return success(groomingEstimateService.getGroomingEstimateDetailInfo(groomingEstimateId));
    }

    @Operation(summary = "사용자에게 미용 견적서 요청", description = "미용 견적서를 요청한 사용자에게 추가 의견을 덧붙여 견적서 전송 (신규 -> 대기)")
    @PostMapping
    public CommonResponseEntity<String> createGroomingEstimate(@RequestBody GroomerGroomingEstimateReq request, PayloadDto payloadDto) {
        groomingEstimateService.createGroomerGroomingEstimate(request, payloadDto.getAccountId());
        sseEmitterService.sendMessageToUser(payloadDto.getAccountId(), REGISTRATION_COMPLETED.getMessage());
        return success(REGISTRATION_COMPLETED.getMessage());
    }
}
