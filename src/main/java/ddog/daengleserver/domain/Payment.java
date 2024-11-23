package ddog.daengleserver.domain;

import ddog.daengleserver.domain.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    private Long id;
    private Long price;
    private PaymentStatus status;
    private String paymentUid;
}
