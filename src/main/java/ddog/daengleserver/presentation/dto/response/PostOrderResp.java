package ddog.daengleserver.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostOrderResp {
    Long userId;
    Long itemId;
    String orderUId;
}
