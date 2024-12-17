package ddog.user.presentation.account.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class WithdrawResp {
    private Long accountId;
    private LocalDateTime withdrawDate;
}
