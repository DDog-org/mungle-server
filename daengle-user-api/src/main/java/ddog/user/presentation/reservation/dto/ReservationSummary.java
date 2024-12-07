package ddog.user.presentation.reservation.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ReservationSummary {
    private Long reservationId;
    private String recipientName;
    private String shopName;
    private LocalDateTime schedule;
}
