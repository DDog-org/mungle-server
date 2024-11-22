package ddog.daengleserver.presentation;

import ddog.daengleserver.application.GroomingEstimateService;
import ddog.daengleserver.global.common.CommonResponseEntity;
import ddog.daengleserver.presentation.dto.response.UserAddressAndPetsInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static ddog.daengleserver.global.common.CommonResponseEntity.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/daengle")
public class GroomingEstimateController {

    private final GroomingEstimateService groomingEstimateService;

    @GetMapping("address-pets-info/{userId}")
    public CommonResponseEntity<UserAddressAndPetsInfo> getAddressAndPetsInfo(@PathVariable Long userId) {
        return success(groomingEstimateService.getUserAddressAndPetsInfoById(userId));
    }
}
