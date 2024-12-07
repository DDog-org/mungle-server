package ddog.user.presentation.reservation;

import ddog.auth.exception.common.CommonResponseEntity;
import ddog.user.application.ReservationService;
import ddog.user.presentation.reservation.dto.ReservationSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
