package ddog.groomer.presentation.account.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WithdrawInfoResp {
    Integer waitingForServiceCount;
}
