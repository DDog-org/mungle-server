package ddog.daengleserver.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostOrderResp {
    Long accountId;
    Long itemId;
    String orderUId;
}
