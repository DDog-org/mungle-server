package ddog.user.presentation.estimate;

import ddog.auth.dto.PayloadDto;
import ddog.auth.exception.common.CommonResponseEntity;
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

    /* 미용사, 사용자 및 반려견 정보 제공 */
    @PostMapping("/groomer-user-info")
    public CommonResponseEntity<UserInfo.Grooming> getGroomerAndUserInfo(@RequestBody GroomerInfoReq request, PayloadDto payloadDto) {
        return success(estimateService.getGroomerAndUserInfo(request.getGroomerId(), payloadDto.getAccountId()));
    }

    /* 병원, 사용자 및 반려견 정보 제공 */
    @PostMapping("/vet-user-info")
    public CommonResponseEntity<UserInfo.Care> getVetAndUserInfo(@RequestBody VetInfoReq request, PayloadDto payloadDto) {
        return success(estimateService.getVetAndUserInfo(request.getVetId(), payloadDto.getAccountId()));
    }

    /* 사용자 -> 미용사 (신규) 미용 견적서 등록 */
    @PostMapping("/grooming")
    public CommonResponseEntity<EstimateResp> createGroomingEstimate(@RequestBody CreateNewGroomingEstimateReq request, PayloadDto payloadDto) {
        return success(estimateService.createNewGroomingEstimate(request, payloadDto.getAccountId()));
    }

    /* 사용자 -> 병원 (신규) 진료 견적서 등록 */
    @PostMapping("/care")
    public CommonResponseEntity<EstimateResp> createCareEstimate(@RequestBody CreateNewCareEstimateReq request, PayloadDto payloadDto) {
        return success(estimateService.createNewCareEstimate(request, payloadDto.getAccountId()));
    }

    /* 미용/진료 (대기) 견적서 상세 조회 */
    @GetMapping("/list")
    public CommonResponseEntity<EstimateInfo> findEstimates(PayloadDto payloadDto) {
        return success(estimateService.findEstimates(payloadDto.getAccountId()));
    }

    /* 사용자가 작성한 미용 견적서 조회 */
    @GetMapping("/request/{groomingEstimateId}")
    public CommonResponseEntity<UserEstimate.Grooming> getGroomingEstimate(@PathVariable Long groomingEstimateId) {
        return success(estimateService.getGroomingEstimate(groomingEstimateId));
    }

    /* 사용자가 작성한 진료 견적서 조회 */
    @GetMapping("/request/{careEstimateId}")
    public CommonResponseEntity<UserEstimate.Care> getCareEstimate(@PathVariable Long careEstimateId) {
        return success(estimateService.getCareEstimate(careEstimateId));
    }

    /* (대기) 미용 견적서 상세 조회 */
    @GetMapping("/{groomingEstimateId}/grooming-detail")
    public CommonResponseEntity<GroomingEstimateDetail> getGroomingEstimateDetail(@PathVariable Long groomingEstimateId) {
        return success(estimateService.getGroomingEstimateDetail(groomingEstimateId));
    }

    /* (대기) 진료 견적서 상세 조회 */
    @GetMapping("/{careEstimateId}/care-detail")
    public CommonResponseEntity<CareEstimateDetail> getCareEstimateDetail(@PathVariable Long careEstimateId) {
        return success(estimateService.getCareEstimateDetail(careEstimateId));
    }
}
