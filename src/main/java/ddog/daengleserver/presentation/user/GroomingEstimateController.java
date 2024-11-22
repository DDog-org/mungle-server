package ddog.daengleserver.presentation.user;

import ddog.daengleserver.application.GroomingEstimateService;
import ddog.daengleserver.global.common.CommonResponseEntity;
import ddog.daengleserver.presentation.dto.request.GroomingEstimateReq;
import ddog.daengleserver.presentation.dto.response.UserAddressAndPetsInfo;
import ddog.daengleserver.presentation.enums.GroomingEstimateControllerResp;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static ddog.daengleserver.global.common.CommonResponseEntity.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/daengle")
public class GroomingEstimateController {

    private final GroomingEstimateService groomingEstimateService;

    @GetMapping("address-pets-info/{userId}")
    public CommonResponseEntity<UserAddressAndPetsInfo> getAddressAndPetsInfo(@PathVariable Long userId) {
        return success(groomingEstimateService.getUserAddressAndPetsInfoById(userId));
    }

    @PostMapping("general-estimate")
    public CommonResponseEntity<String> createGeneralGroomingEstimate(@RequestBody GroomingEstimateReq groomingEstimateReq) {
        groomingEstimateService.createGeneralGroomingEstimate(groomingEstimateReq);
        return success(GroomingEstimateControllerResp.REGISTRATION_COMPLETED.getMessage());
    }
}
