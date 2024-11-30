package ddog.user.presentation;

import ddog.auth.dto.PayloadDto;
import ddog.domain.estimate.dto.request.UserDesignationCareEstimateReq;
import ddog.domain.estimate.dto.request.UserDesignationGroomingEstimateReq;
import ddog.domain.estimate.dto.request.UserGeneralCareEstimateReq;
import ddog.domain.estimate.dto.request.UserGeneralGroomingEstimateReq;
import ddog.domain.estimate.dto.response.CareEstimateDetail;
import ddog.domain.estimate.dto.response.EstimateInfo;
import ddog.domain.estimate.dto.response.GroomingEstimateDetail;
import ddog.user.application.EstimateService;
import ddog.user.application.exception.common.CommonResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static ddog.domain.estimate.enums.UserEstimateControllerResp.*;
import static ddog.user.application.exception.common.CommonResponseEntity.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/daengle/estimate")
public class EstimateController {

    private final EstimateService estimateService;

    @PostMapping("/general-grooming")
    public CommonResponseEntity<String> createGeneralGroomingEstimate(@RequestBody UserGeneralGroomingEstimateReq request, PayloadDto payloadDto) {
        estimateService.createUserGeneralGroomingEstimate(request, payloadDto.getAccountId());
        return success(GENERAL_GROOMING_REGISTRATION.getMessage());
    }

    @PostMapping("/designation-grooming")
    public CommonResponseEntity<String> createDesignationGroomingEstimate(@RequestBody UserDesignationGroomingEstimateReq request, PayloadDto payloadDto) {
        estimateService.createUserDesignationGroomingEstimate(request, payloadDto.getAccountId());
        return success(DESIGNATION_GROOMING_REGISTRATION.getMessage());
    }

    @PostMapping("/general-care")
    public CommonResponseEntity<String> createGeneralCareEstimate(@RequestBody UserGeneralCareEstimateReq request, PayloadDto payloadDto) {
        estimateService.createUserGeneralCareEstimate(request, payloadDto.getAccountId());
        return success(GENERAL_CARE_REGISTRATION.getMessage());
    }

    @PostMapping("/designation-care")
    public CommonResponseEntity<String> createDesignationCareEstimate(@RequestBody UserDesignationCareEstimateReq request, PayloadDto payloadDto) {
        estimateService.createUserDesignationCareEstimate(request, payloadDto.getAccountId());
        return success(DESIGNATION_CARE_REGISTRATION.getMessage());
    }

    @GetMapping("/list")
    public CommonResponseEntity<EstimateInfo> findEstimateInfo(PayloadDto payloadDto) {
        return success(estimateService.findEstimateInfo(payloadDto.getAccountId()));
    }

    @GetMapping("/{groomingEstimateId}/grooming-detail")
    public CommonResponseEntity<GroomingEstimateDetail> getGroomingEstimateDetail(@PathVariable Long groomingEstimateId) {
        return success(estimateService.getGroomingEstimateDetail(groomingEstimateId));
    }

    @GetMapping("/{careEstimateId}/care-detail")
    public CommonResponseEntity<CareEstimateDetail> getCareEstimateDetail(@PathVariable Long careEstimateId) {
        return success(estimateService.getCareEstimateDetail(careEstimateId));
    }
}
