package ddog.daengleserver.presentation.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PostOrderResp {
    ORDER_REGISTRATION_COMPLETED("주문 등록 완료");

    String message;
}
