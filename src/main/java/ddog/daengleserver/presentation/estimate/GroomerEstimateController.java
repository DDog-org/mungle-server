package ddog.daengleserver.presentation.estimate;

import ddog.daengleserver.application.GroomingEstimateService;
import ddog.daengleserver.global.common.CommonResponseEntity;
import ddog.daengleserver.presentation.dto.response.GroomingEstimateInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/groomer")
@RequiredArgsConstructor
public class GroomerEstimateController {

    private final GroomingEstimateService groomingEstimateService;

    @GetMapping("/estimate-info/{groomerId}")
    public CommonResponseEntity<List<GroomingEstimateInfo>> findGroomingEstimateInfos(@PathVariable Long groomerId) {
        return CommonResponseEntity.success(groomingEstimateService.findGroomingEstimateInfos(groomerId));
    }
}
