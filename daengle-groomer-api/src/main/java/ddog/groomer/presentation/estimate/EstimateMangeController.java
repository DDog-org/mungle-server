package ddog.groomer.presentation.estimate;

import ddog.auth.dto.PayloadDto;
import ddog.auth.exception.common.CommonResponseEntity;
import ddog.groomer.application.EstimateManageService;
import ddog.groomer.presentation.estimate.dto.ReservationEstimateContent;
import ddog.groomer.presentation.estimate.dto.WeekScheduleResp;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static ddog.auth.exception.common.CommonResponseEntity.success;

@RestController
@RequestMapping("/api/groomer/manage")
@RequiredArgsConstructor
public class EstimateMangeController {
    private final EstimateManageService estimateManageService;

    @GetMapping("/reservation/{reservationId}")
    public CommonResponseEntity<ReservationEstimateContent> findReservationOrEstimateBy(@PathVariable Long reservationId, PayloadDto payloadDto) {
        return success(estimateManageService.findEstimateDetailByGroomerIdAndPetId(payloadDto.getAccountId(), reservationId));
    }

}
