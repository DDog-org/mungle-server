package ddog.user.presentation.reservation;

import ddog.domain.payment.Reservation;
import ddog.user.application.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/{reservationId}/review")
    public Reservation review(@PathVariable Long reservationId) {

    }


}
