package ddog.daengleserver.presentation.estimate;

import ddog.daengleserver.application.GroomingEstimateService;
import ddog.daengleserver.global.auth.dto.PayloadDto;
import ddog.daengleserver.global.common.CommonResponseEntity;
import ddog.daengleserver.presentation.estimate.dto.request.GroomerGroomingEstimateReq;
import ddog.daengleserver.presentation.estimate.dto.response.UserGroomingEstimateDetails;
import ddog.daengleserver.presentation.estimate.dto.response.GroomingEstimateInfos;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ddog.daengleserver.global.common.CommonResponseEntity.success;
import static ddog.daengleserver.presentation.estimate.enums.GroomerEstimateControllerResp.REGISTRATION_COMPLETED;

@Tag(name = "미용사 미용 견적서 관련 API")
@RestController
@RequestMapping("/api/groomer/estimate")
@RequiredArgsConstructor
public class GroomerEstimateController {

    private final GroomingEstimateService groomingEstimateService;

    @Operation(summary = "사용자가 요청한 미용 견적서 목록 조회", description = "같은 주소에 위치한 사용자가 요청한 견적서 목록 조회 (신규)")
    @GetMapping("/list")
    public CommonResponseEntity<GroomingEstimateInfos> findGroomingEstimateInfos(PayloadDto payloadDto) {
        return success(groomingEstimateService.findGroomingEstimateInfos(payloadDto.getAccountId()));
    }

    @Operation(summary = "미용 견적서 상세 내용 조회")
    @GetMapping("/{groomingEstimateId}/details")
    public CommonResponseEntity<UserGroomingEstimateDetails> getGroomingEstimateDetails(@PathVariable Long groomingEstimateId) {
        return success(groomingEstimateService.getGroomingEstimateDetailInfo(groomingEstimateId));
    }

    @Operation(summary = "사용자에게 미용 견적서 요청", description = "미용 견적서를 요청한 사용자에게 추가 의견을 덧붙여 견적서 전송 (신규 -> 대기)")
    @PostMapping
    public CommonResponseEntity<String> createGroomingEstimate(@RequestBody GroomerGroomingEstimateReq request, PayloadDto payloadDto) {
        groomingEstimateService.createGroomerGroomingEstimate(request, payloadDto.getAccountId());
        return success(REGISTRATION_COMPLETED.getMessage());
    }
}
