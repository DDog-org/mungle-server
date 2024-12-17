package ddog.user.presentation.account.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WithdrawResp {
    Integer waitingForServiceCount;
}
