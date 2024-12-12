package ddog.user.presentation.reservation;

import ddog.auth.dto.PayloadDto;
import ddog.auth.exception.common.CommonResponseEntity;
import ddog.user.application.ReservationService;
import ddog.user.presentation.reservation.dto.EstimateInfo;
import ddog.user.presentation.reservation.dto.ReservationSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static ddog.auth.exception.common.CommonResponseEntity.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/{reservationId}/review")
    public CommonResponseEntity<ReservationSummary> getReservationSummary(@PathVariable Long reservationId) {
        return success(reservationService.getReservationSummary(reservationId));
    }

    @GetMapping("/grooming/list")
    public CommonResponseEntity<EstimateInfo.Grooming> findGroomingEstimates(PayloadDto payloadDto) {
        return success(reservationService.findGroomingEstimates(payloadDto.getAccountId()));
    }

    @GetMapping("/care/list")
    public CommonResponseEntity<EstimateInfo.Care> findCareEstimates(PayloadDto payloadDto) {
        return success(reservationService.findCareEstimates(payloadDto.getAccountId()));
    }
}
