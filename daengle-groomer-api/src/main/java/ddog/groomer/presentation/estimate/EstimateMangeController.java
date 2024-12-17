package ddog.groomer.presentation.estimate;

import ddog.auth.exception.common.CommonResponseEntity;
import ddog.groomer.presentation.estimate.dto.ReservationEstimateContent;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static ddog.auth.exception.common.CommonResponseEntity.success;

@RestController
@RequestMapping("/api/groomer/manage")
@RequiredArgsConstructor
public class EstimateMangeController {

    @GetMapping("/reservation/{reservationId}")
    public CommonResponseEntity<ReservationEstimateContent> findReservationOrEstimateBy(@PathVariable Long reservationId) {
        return success(ReservationEstimateContent.builder()

                .build());
    }

}
