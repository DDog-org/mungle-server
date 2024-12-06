package ddog.user.presentation.estimate;

import ddog.auth.dto.PayloadDto;
import ddog.auth.exception.common.CommonResponseEntity;
import ddog.domain.notification.enums.NotifyType;
import ddog.notification.application.NotificationService;
import ddog.user.application.EstimateService;
import ddog.user.presentation.estimate.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static ddog.auth.exception.common.CommonResponseEntity.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/estimate")
public class EstimateController {

    private final EstimateService estimateService;
    private final NotificationService notificationService;

    @PostMapping("/groomer-user-info")
    public CommonResponseEntity<AccountInfo.Grooming> getGroomerAndUserInfo(@RequestBody GroomerInfoReq request, PayloadDto payloadDto) {
        return success(estimateService.getGroomerAndUserInfo(request.getGroomerId(), payloadDto.getAccountId()));
    }

    @PostMapping("/vet-user-info")
    public CommonResponseEntity<AccountInfo.Care> getVetAndUserInfo(@RequestBody VetInfoReq request, PayloadDto payloadDto) {
        return success(estimateService.getVetAndUserInfo(request.getVetId(), payloadDto.getAccountId()));
    }

    @PostMapping("/grooming")
    public CommonResponseEntity<EstimateResp> createGroomingEstimate(@RequestBody GroomingEstimateReq request, PayloadDto payloadDto) {
        notificationService.sendNotifiacationToUser(request.getGroomerId(), NotifyType.RESERVED, "예약완료");
        return success(estimateService.createGroomingEstimate(request, payloadDto.getAccountId()));
    }

    @PostMapping("/care")
    public CommonResponseEntity<EstimateResp> createCareEstimate(@RequestBody CareEstimateReq request, PayloadDto payloadDto) {
        return success(estimateService.createCareEstimate(request, payloadDto.getAccountId()));
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
