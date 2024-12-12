package ddog.payment.application.dto.response;

import ddog.domain.payment.enums.ReservationStatus;
import ddog.domain.payment.enums.ServiceType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PaymentHistoryDetail {
    private Long reservationId;
    private ReservationStatus reservationStatus;
    private String recipientName;
    private String shopName;
    private LocalDateTime schedule;
    private Long deposit;
    private String customerName;
    private String customerPhoneNumber;
    private String visitorName;
    private String visitorPhoneNumber;
}
