package ddog.domain.payment.dto;

import ddog.domain.payment.enums.ServiceType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostOrderInfo {
    private Long estimateId;
    private ServiceType serviceType;
    private String recipientName;
    private String shopName;
    private LocalDateTime schedule;
    private Long price;
    private String customerName;
    private String customerPhoneNumber;
    private String visitorName;
    private String visitorPhoneNumber;
}
