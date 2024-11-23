package ddog.daengleserver.presentation.estimate;

import ddog.daengleserver.application.GroomingEstimateService;
import ddog.daengleserver.global.auth.dto.PayloadDto;
import ddog.daengleserver.global.common.CommonResponseEntity;
import ddog.daengleserver.presentation.dto.request.GroomerGroomingEstimateReq;
import ddog.daengleserver.presentation.dto.response.UserGroomingEstimateDetails;
import ddog.daengleserver.presentation.dto.response.UserGroomingEstimateInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ddog.daengleserver.global.common.CommonResponseEntity.success;
import static ddog.daengleserver.presentation.enums.GroomerEstimateControllerResp.GENERAL_REGISTRATION_COMPLETED;

@RestController
@RequestMapping("/api/groomer")
@RequiredArgsConstructor
public class GroomerEstimateController {

    private final GroomingEstimateService groomingEstimateService;

    @GetMapping("/estimate-info")
    public CommonResponseEntity<List<UserGroomingEstimateInfo>> findGroomingEstimateInfos(PayloadDto payloadDto) {
        return success(groomingEstimateService.findGroomingEstimateInfos(payloadDto.getAccountId()));
    }

    @GetMapping("/estimate-details/{groomingEstimateId}")
    public CommonResponseEntity<UserGroomingEstimateDetails> getGroomingEstimateDetails(@PathVariable Long groomingEstimateId) {
        return success(groomingEstimateService.getGroomingEstimateDetailInfo(groomingEstimateId));
    }

    @PostMapping("/general-estimate")
    public CommonResponseEntity<String> createGeneralGroomingEstimate(@RequestBody GroomerGroomingEstimateReq request, PayloadDto payloadDto) {
        groomingEstimateService.createGroomerGeneralGroomingEstimate(request, payloadDto.getAccountId());
        return success(GENERAL_REGISTRATION_COMPLETED.getMessage());
    }
}
