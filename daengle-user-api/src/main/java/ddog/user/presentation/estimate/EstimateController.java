package ddog.user.presentation.estimate;

import ddog.auth.dto.PayloadDto;
import ddog.domain.notification.enums.NotifyType;
import ddog.notification.application.NotificationService;
import ddog.user.presentation.estimate.dto.DesignationCareEstimateReq;
import ddog.user.presentation.estimate.dto.DesignationGroomingEstimateReq;
import ddog.user.presentation.estimate.dto.GeneralCareEstimateReq;
import ddog.user.presentation.estimate.dto.GeneralGroomingEstimateReq;
import ddog.user.presentation.estimate.dto.CareEstimateDetail;
import ddog.user.presentation.estimate.dto.EstimateInfo;
import ddog.user.presentation.estimate.dto.GroomingEstimateDetail;
import ddog.user.application.EstimateService;
import ddog.user.application.exception.common.CommonResponseEntity;
import ddog.user.presentation.estimate.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static ddog.user.presentation.estimate.EstimateControllerResp.*;

import static ddog.user.application.exception.common.CommonResponseEntity.success;
import static ddog.user.presentation.estimate.EstimateControllerResp.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/daengle/estimate")
public class EstimateController {

    private final EstimateService estimateService;
    private final NotificationService notificationService;

    @PostMapping("/general-grooming")
    public CommonResponseEntity<String> createGeneralGroomingEstimate(@RequestBody GeneralGroomingEstimateReq request, PayloadDto payloadDto) {
        estimateService.createGeneralGroomingEstimate(request, payloadDto.getAccountId());
        return success(GENERAL_GROOMING_REGISTRATION.getMessage());
    }

    @PostMapping("/designation-grooming")
    public CommonResponseEntity<String> createDesignationGroomingEstimate(@RequestBody DesignationGroomingEstimateReq request, PayloadDto payloadDto) throws IOException {
        estimateService.createDesignationGroomingEstimate(request, payloadDto.getAccountId());
        notificationService.sendNotification(request.getGroomerId(), NotifyType.CALL, "지정 견적서가 도착했습니다!");
        return success(DESIGNATION_GROOMING_REGISTRATION.getMessage());
    }

    @PostMapping("/general-care")
    public CommonResponseEntity<String> createGeneralCareEstimate(@RequestBody GeneralCareEstimateReq request, PayloadDto payloadDto) {
        estimateService.createGeneralCareEstimate(request, payloadDto.getAccountId());
        return success(GENERAL_CARE_REGISTRATION.getMessage());
    }

    @PostMapping("/designation-care")
    public CommonResponseEntity<String> createDesignationCareEstimate(@RequestBody DesignationCareEstimateReq request, PayloadDto payloadDto) throws IOException {
        estimateService.createDesignationCareEstimate(request, payloadDto.getAccountId());
        notificationService.sendNotification(request.getVetId(), NotifyType.CALL, "지정 견적서가 도착했습니다!");
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

    @PostMapping("/groomer-user-info")
    public CommonResponseEntity<AccountInfo.Grooming> getGroomerAndUserInfo(@RequestBody GroomerInfoReq request, PayloadDto payloadDto) {
        return success(estimateService.getGroomerAndUserInfo(request.getGroomerId(), payloadDto.getAccountId()));
    }

    @PostMapping("/vet-user-info")
    public CommonResponseEntity<AccountInfo.Care> getVetAndUserInfo(@RequestBody VetInfoReq request, PayloadDto payloadDto) {
        return success(estimateService.getVetAndUserInfo(request.getVetId(), payloadDto.getAccountId()));
    }
}
