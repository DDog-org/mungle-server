package ddog.groomer.presentation.estimate;

import ddog.auth.exception.common.CommonResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/groomer/manage")
@RequiredArgsConstructor
public class EstimateMangeController {

//    @GetMapping("/reservation/{reservationId}")
//    public CommonResponseEntity<ReservationEstimateContent> findReservationOrEstimateBy(@PathVariable Long reservationId) {
//        return success
//    }

}
